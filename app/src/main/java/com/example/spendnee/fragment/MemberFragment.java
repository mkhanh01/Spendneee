package com.example.spendnee.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spendnee.R;
import com.example.spendnee.adapter.GroupAdapter;
import com.example.spendnee.databinding.FragmentCreatorBinding;
import com.example.spendnee.databinding.FragmentMemberBinding;
import com.example.spendnee.fragment.GroupTransactionFragment;
import com.example.spendnee.model.Group;
import com.example.spendnee.viewmodel.GroupViewModel;


public class MemberFragment extends Fragment {

    private FragmentMemberBinding binding;
    private GroupViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMemberBinding.inflate(inflater, container, false);
        init();
        viewModel.refreshLiveDataMember();

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
                bundle.putString("member", "member");

                GroupTransactionFragment groupTransactionFragment = new GroupTransactionFragment();
                groupTransactionFragment.setArguments(bundle);

                binding.fragmentMemberLinearFrame2.setVisibility(View.GONE);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_member_frame1, groupTransactionFragment);

                transaction.commit();
            }
        });

        viewModel = new ViewModelProvider(getActivity()).get(GroupViewModel.class);

        viewModel.getMemberGroup().observe(getViewLifecycleOwner(), groups1 -> {
            adapter.setGroups(groups1);
        });

        binding.fragmentMemberRvListTrans.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        binding.fragmentMemberRvListTrans.setAdapter(adapter);

    }
}