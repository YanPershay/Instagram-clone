package pershay.bstu.woolfy.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.retrofit.APIClient;
import pershay.bstu.woolfy.retrofit.JsonPlaceHolderApi;
import pershay.bstu.woolfy.ViewPostFragment;
import pershay.bstu.woolfy.models.Post;
import pershay.bstu.woolfy.models.UserData;
import pershay.bstu.woolfy.utils.MainfeedListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainFragment extends Fragment {

    private ArrayList<Post> mPhotos;
    private ListView mListView;
    private MainfeedListAdapter mAdapter;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = view.findViewById(R.id.listView);
        mPhotos = new ArrayList<>();
        refreshLayout = view.findViewById(R.id.refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            refreshLayout.setRefreshing(true);
            progressBar.setVisibility(View.VISIBLE);
            try{
                mAdapter.notifyDataSetChanged();
            } catch (Exception ex){
                getPosts();
            }

            getPosts();

            refreshLayout.setRefreshing(false);
            }
        });

        progressBar = view.findViewById(R.id.progressBar);

        jsonPlaceHolderApi = APIClient.getClient().create(JsonPlaceHolderApi.class);

        getPosts();

        return view;
    }

    private void getPosts() {

        mPhotos.clear();
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                //AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());
                //dlgAlert.setMessage("Posts loaded!");
                //dlgAlert.setTitle("Success!");
                //dlgAlert.setPositiveButton("OK", null);
                //dlgAlert.setCancelable(true);
                //dlgAlert.create().show();

                List<Post> posts = response.body();
                try{
                    for (Post post:
                            posts) {
                        mPhotos.add(post);
                    }

                    displayPhotos();
                    progressBar.setVisibility(View.INVISIBLE);
                }
                catch (Exception ex){
                    AlertDialog.Builder dlgErr  = new AlertDialog.Builder(getContext());
                    dlgErr.setMessage("Bad connection. Try again");
                    dlgErr.setTitle("Response");
                    dlgErr.setPositiveButton("OK", null);
                    dlgErr.setCancelable(true);
                    dlgErr.create().show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
                dlgAlert.setMessage("Bad connection. Try again.");
                dlgAlert.setTitle("Fail");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void displayPhotos(){
        if(mPhotos != null){
        try{
            mAdapter = new MainfeedListAdapter(getActivity(), R.layout.layout_mainfeed_listitem, mPhotos);
            mListView.setAdapter(mAdapter);
        } catch (IndexOutOfBoundsException e){
            Log.e("MainFragment", "displayPhotos: IndexOutOfBoundsException:" + e.getMessage() );
        } catch (NullPointerException e){
            Log.e("MainFragment", "displayPhotos: NullPointerException:" + e.getMessage() );
            }
        }
    }

}

