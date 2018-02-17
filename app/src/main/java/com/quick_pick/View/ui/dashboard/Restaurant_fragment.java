package com.quick_pick.View.ui.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quick_pick.Model.restaurant_category.RestaurantData;
import com.quick_pick.Model.restaurant_category.Restaurant_names;
import com.quick_pick.Presenter.services.Network.APIResponse;
import com.quick_pick.Presenter.services.Network.RetrofitClient;
import com.quick_pick.R;
import com.quick_pick.View.adapters.ShowRestaurant_Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh kumar on 19-11-2017.
 */

public class Restaurant_fragment extends Fragment {
    View view;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.txt_no_res)
    TextView txt_no_res;

    Bundle bundle;
    int from;
    ArrayList<RestaurantData> dataList = new ArrayList<>();
    Restaurant_names restaurant_name, temp_pojo;
    int spage = 1, no_records = 0;
    private boolean loading;
    private boolean scrollflag = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.resatarunt_list_fragment,container,false);
        ButterKnife.bind(this,view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.addOnScrollListener(new EndlessScrollListener(recyclerview));
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            bundle = this.getArguments();
            from = bundle.getInt("from", 0);
            fetchRestaurent(0);
            Log.e("from", "from:" + from);
        }
    }

    private void fetchRestaurent(int number_of_records){
        RetrofitClient.getInstance().getEndPoint(getActivity(),"").getResult(getParams(number_of_records),new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("response is ","<><>"+res);
                  restaurant_name = new Gson().fromJson(res,Restaurant_names.class);
                if(restaurant_name.getStatus().equalsIgnoreCase("successfully")){
                    if (spage == 1) {
                        temp_pojo = restaurant_name;
                    } else {
                        dataList = (ArrayList<RestaurantData>) temp_pojo.getRestaurantData();
                        dataList.addAll(restaurant_name.getRestaurantData());
                        temp_pojo.setRestaurantData(dataList);
                    }

                    recyclerview.setAdapter(new ShowRestaurant_Adapter(getActivity(),restaurant_name.getRestaurantData()));
                    if(restaurant_name.getRestaurantData().size()==0){
                        txt_no_res.setVisibility(View.VISIBLE);
                    }


                    if (restaurant_name.getRestaurantData().size() < 20) {
                        scrollflag = false;
                    }

                }else{
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(String res) {

            }
        });
    }


    private Map<String ,String > getParams(int number_of_records){
        Map<String ,String > params = new HashMap<>();
        params.put("latitude","");
        params.put("longitude","");
        params.put("CityId",((GetCity_id)DashBoardActivity.instance).getCity_id());
        params.put("category_Id",""+(from+1));
        params.put("FlagSlNo",""+number_of_records);
        params.put("action",getActivity().getResources().getString(R.string.getResturants));
        return params;
    }

    public class EndlessScrollListener extends RecyclerView.OnScrollListener {
        private RecyclerView listView;

        public EndlessScrollListener(RecyclerView listView) {
            this.listView = listView;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        }

        @Override
        public void onScrollStateChanged(RecyclerView view, int scrollState) {

            LinearLayoutManager layoutManager = ((LinearLayoutManager) view.getLayoutManager());
            int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
            if (null != temp_pojo) {
                if (scrollState == 0 && scrollflag
                        && lastVisiblePosition == listView.getAdapter().getItemCount() - 1) {
                    if (!loading) {
                        loading = true;
                        spage += 1;
                        no_records += 20;
                        fetchRestaurent(no_records);
//                            getOnlineData(from, getparams(from, no_records, keywordtofind), "leads");

                    }
                }
            }
        }
    }
}
