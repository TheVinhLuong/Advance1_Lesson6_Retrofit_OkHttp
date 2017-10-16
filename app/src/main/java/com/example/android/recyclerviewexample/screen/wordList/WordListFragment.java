package com.example.android.recyclerviewexample.screen.wordList;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.recyclerviewexample.R;
import com.example.android.recyclerviewexample.data.word.Word;
import com.example.android.recyclerviewexample.data.word.WordRepository;
import com.example.android.recyclerviewexample.data.word.local.WordLocalDatasource;
import com.example.android.recyclerviewexample.databinding.FragmentWordListBinding;
import com.example.android.recyclerviewexample.screen.BaseFragment;
import java.util.ArrayList;

/**
 * WordList Screen.
 */
public class WordListFragment extends BaseFragment implements WordListContract.View{
    private static final String TAG = WordListFragment.class.getSimpleName();
    private WordListContract.ViewModel mViewModel;
    private WordListAdapter mWordListAdapter;
    private WordRepository mWordRepository;
    private FragmentWordListBinding mBinding;
    private WordListContract.View mView;

    public WordListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = this;
        ArrayList<Word> words = new ArrayList<>();
        mWordRepository = WordRepository.getInstance(new WordLocalDatasource(getActivity()));
        mWordListAdapter = new WordListAdapter(getActivity(), words);
        mViewModel = new WordListViewModel(mWordListAdapter, mView);
        WordListContract.Presenter presenter = new WordListPresenter(mViewModel, mWordRepository);
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_word_list, container, false);
        mBinding.setViewModel((WordListViewModel) mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }

    @Override
    public void updateTextView(Word word) {
        Log.d(TAG,"update text view: " + word.getWord());
        getActivity().runOnUiThread(() -> {
            mBinding.setWord(word);
            mBinding.notifyChange(); 
        });
    }
}
