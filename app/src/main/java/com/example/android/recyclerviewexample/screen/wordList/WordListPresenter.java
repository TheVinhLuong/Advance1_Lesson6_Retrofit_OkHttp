package com.example.android.recyclerviewexample.screen.wordList;

import android.util.Log;
import com.example.android.recyclerviewexample.data.NetworkRequestGenerator;
import com.example.android.recyclerviewexample.data.word.Word;
import com.example.android.recyclerviewexample.data.word.WordDataSource;
import com.example.android.recyclerviewexample.data.word.WordRepository;
import com.example.android.recyclerviewexample.data.word.remote.ICommunicatorService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Listens to user actions from the UI ({@link WordListFragment}), retrieves the data and updates
 * the UI as required.
 */
final class WordListPresenter implements WordListContract.Presenter {
    private static final String TAG = WordListPresenter.class.getSimpleName();
    private final WordListContract.ViewModel mViewModel;
    private final WordRepository mWordRepository;
    private List<Word> mWords;

    public WordListPresenter(WordListContract.ViewModel viewModel, WordRepository wordRepository) {
        mViewModel = viewModel;
        mWordRepository = wordRepository;
    }

    @Override
    public void onStart() {
        if (mWordRepository != null) {
            mWordRepository.deleteAll();
            mWordRepository.addWord(new Word("a"));
            mWordRepository.addWord(new Word("b"));
            mWordRepository.addWord(new Word("c"));
            mWordRepository.getAllWords(new WordDataSource.LoadAllWordsCallback() {
                @Override
                public void onAllWordLoaded(List<Word> words) {
                    mWords = words;
                    mViewModel.changeDataSet(mWords);
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        }
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onItemWordListClicked(Word word) {
        Log.d(TAG, word.getWord() + " " + WordListPresenter.class.getName());
    }

    @Override
    public void onMakeRequestButtonClicked() {
        NetworkRequestGenerator.createService(ICommunicatorService.class).getTokenizedString("cảnh sát giao thông")
                .enqueue(new Callback<Word>() {
                    @Override
                    public void onResponse(Call<Word> call, Response<Word> response) {
                        if(response.isSuccessful()) {
                            mViewModel.updateTextView(new Word(100, response.body().getWord()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Word> call, Throwable t) {

                    }
                });
    }
}
