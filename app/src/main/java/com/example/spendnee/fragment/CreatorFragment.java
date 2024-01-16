package com.example.spendnee.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spendnee.R;
import com.example.spendnee.adapter.GroupAdapter;
import com.example.spendnee.databinding.FragmentCreatorBinding;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Group;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.viewmodel.GroupViewModel;

import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreatorFragment extends Fragment {
    private FragmentCreatorBinding binding;
    private GroupViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatorBinding.inflate(inflater, container, false);
        init();

        viewModel.refreshLiveData();

        return binding.getRoot();
    }

    private void init(){
        GroupAdapter adapter = new GroupAdapter(getContext(), new GroupAdapter.GroupClickListener() {
            @Override
            public void onGroupClicked(Group group) {

                Bundle bundle = new Bundle();
                bundle.putInt("idGroup", group.getIdGroup());
                bundle.putString("nameGroup", group.getNameGroup());
                bundle.putString("categoryImage", group.getCategoryImage());
                bundle.putString("member", "creator");

                GroupTransactionFragment groupTransactionFragment = new GroupTransactionFragment();
                groupTransactionFragment.setArguments(bundle);

                binding.fragmentCreatorLinearFrame2.setVisibility(View.GONE);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_creator_frame1, groupTransactionFragment);

                transaction.commit();
            }
        });

        viewModel = new ViewModelProvider(getActivity()).get(GroupViewModel.class);
     //   allGroupViewModel = new ViewModelProvider(getActivity()).get(AllGroupViewModel.class);

        viewModel.getGroupList().observe(getViewLifecycleOwner(), groups -> {
            adapter.setGroups(groups);
        });

        binding.fragmentCreatorRvListTrans.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        binding.fragmentCreatorRvListTrans.setAdapter(adapter);

        binding.fragmentCreatorBtnAddNewGroup.setOnClickListener(c -> {
            callDialogAddGroup();
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                List<Group> groupList = adapter.getGroups();
                Group group = groupList.get(position);

                if (direction == ItemTouchHelper.LEFT){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle(getString(R.string.title_dialog));
                    builder.setMessage(getString(R.string.delete_message));
                    builder.setCancelable(false);

                    builder.setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteNameGroup(group.getIdGroup());
                        }
                    });

                    builder.setNegativeButton(getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            viewModel.refreshLiveData();
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    callDialogAddGroupUpdate(getString(R.string.update), group.getNameGroup());
                    viewModel.refreshLiveData();
                }
            }
        }).attachToRecyclerView(binding.fragmentCreatorRvListTrans);
    }

    private void deleteNameGroup(int idGroup){
        viewModel.deleteGroupName(idGroup).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.body() != null){
                    if (response.body().getIsSuccess().equals(Constants.successfully)){
                        StyleableToast.makeText(getContext(), getString(R.string.deleted_successfully),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                        viewModel.refreshLiveData();
                    }else {
                        StyleableToast.makeText(getContext(), getString(R.string.deleted_unsuccessfully),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                StyleableToast.makeText(getContext(),t.getMessage(), Toast.LENGTH_LONG,
                        R.style.myToast).show();
            }
        });
    }

    private void callDialogAddGroupUpdate(String str, String nameGroup){
        DialogFragment dialogFragment = DialogAddGroup.newInstance();

        Bundle bundle =  new Bundle();
        bundle.putString(getString(R.string.check_update),str);
        bundle.putString( getString(R.string.nameGroup), nameGroup);

        dialogFragment.setArguments(bundle);

        dialogFragment.show(getActivity().getSupportFragmentManager(), "DialogAddGroup");
    }

    private void callDialogAddGroup(){
        DialogFragment dialogFragment =DialogAddGroup.newInstance();

        Bundle bundle =  new Bundle();
        bundle.putString(getString(R.string.check_update), getString(R.string.insert));

        dialogFragment.setArguments(bundle);

        dialogFragment.show(getActivity().getSupportFragmentManager(), "DialogAddGroup");
    }
}