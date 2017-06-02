package com.alvarado.edwin.pruebaandroid.Controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alvarado.edwin.pruebaandroid.Modelo.Adapters.FarmerAdapter;
import com.alvarado.edwin.pruebaandroid.Modelo.Adapters.SimpleDividerItemDecoration;
import com.alvarado.edwin.pruebaandroid.Modelo.DB.DataBaseHelper;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Category;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Farmer;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Item;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.LinkItem;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Location1;
import com.alvarado.edwin.pruebaandroid.Modelo.Servicio.ApiClient;
import com.alvarado.edwin.pruebaandroid.Modelo.Servicio.ApiService;
import com.alvarado.edwin.pruebaandroid.R;
import com.alvarado.edwin.pruebaandroid.info.hoang8f.widget.FButton;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edwinalvarado on 02/06/17.
 */

public class QueryFragment extends Fragment {

    FButton fbSearch;
    FButton fbSearchAll;
    EditText etCategory;
    EditText etItem;
    DataBaseHelper dataBaseHelper = null;
    RecyclerView rvFarmerList;
    ApiService apiInterface;
    FarmerAdapter farmerAdapter;
    TextView tvLoading;
    String vCategory = "";
    String vItem = "";

    public QueryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.query_layout, parent, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        apiInterface = ApiClient.getClient().create(ApiService.class);

        fbSearch = (FButton) view.findViewById(R.id.fbSearch);
        fbSearchAll = (FButton) view.findViewById(R.id.fbSearchAll);
        etCategory = (EditText) view.findViewById(R.id.etCategory);
        etItem = (EditText) view.findViewById(R.id.etItem);
        rvFarmerList = (RecyclerView) view.findViewById(R.id.rvQuery);
        tvLoading = (TextView) view.findViewById(R.id.tvLoading);

        fbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                rvFarmerList.setVisibility(View.GONE);
                tvLoading.setVisibility(View.VISIBLE);
                try {
                    vCategory = etCategory.getText().toString();
                    vItem = etItem.getText().toString();


                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Call call = apiInterface.getData(vCategory, vItem);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                        ArrayList<Farmer> resources = (ArrayList<Farmer>) response.body();

                        try {
                            final Dao<Farmer, Integer> farmerDao = getHelper().getFarmerDao();
                            final Dao<Category, Integer> categoryDao = getHelper().getCategoryDao();
                            final Dao<Item, Integer> itemDao = getHelper().getItemDao();
                            final Dao<LinkItem, Integer> linkitemDao = getHelper().getLinkItemDao();
                            final Dao<Location1, Integer> location1Dao = getHelper().getLocation1Dao();
                            for (Farmer rm : resources) {

                                Farmer auxFarmer = new Farmer();
                                Category auxCategory = new Category();
                                Item auxItem = new Item();
                                LinkItem auxLinkItem = new LinkItem();


                                if (rm.getWebsite() != null)
                                    rm.setUrl(rm.getWebsite().getUrl());
                                else
                                    rm.setUrl("No Url Available");

                                auxFarmer.setFarmerId(rm.getFarmerId());
                                auxFarmer.setZipcode(rm.getZipcode());
                                auxFarmer.setPhone1(rm.getPhone1());
                                auxFarmer.setLocation1(rm.getLocation1());
                                auxFarmer.setUrl(rm.getUrl());
                                auxFarmer.setBusiness(rm.getBusiness());
                                auxFarmer.setFarmName(rm.getFarmName());

                                if (farmerDao.queryForId(rm.getFarmerId()) == null) {
                                    location1Dao.create(rm.getLocation1());
                                    farmerDao.create(auxFarmer);

                                }
                                auxCategory.setCategoryId(rm.getCategory());
                                if (!(categoryDao.queryBuilder()
                                        .where()
                                        .eq("CategoryID", rm.getCategory())
                                        .query().size() > 0)) {
                                    categoryDao.create(auxCategory);
                                }
                                auxItem.setItemId(rm.getItem());
                                if (!(itemDao.queryBuilder()
                                        .where()
                                        .eq("ItemId", rm.getItem())
                                        .query().size() > 0)) {
                                    itemDao.create(auxItem);
                                }
                                if (!(linkitemDao.queryBuilder()
                                        .where()
                                        .eq("Item_id", rm.getItem())
                                        .and()
                                        .eq("Category_id", rm.getCategory())
                                        .and()
                                        .eq("Farmer_id", rm.getFarmerId())
                                        .query().size() > 0)) {
                                    auxLinkItem.setItemId(auxItem);
                                    auxLinkItem.setFarmerId(auxFarmer);
                                    auxLinkItem.setCategory(auxCategory);
                                    linkitemDao.create(auxLinkItem);
                                }


                            }


                            List<LinkItem> linkItemList = linkitemDao.queryBuilder()
                                    .where()
                                    .eq("Item_id", vItem)
                                    .and()
                                    .eq("Category_id", vCategory)
                                    .query();

                            for (LinkItem linkItem : linkItemList) {
                                linkItem.setFarmerId(farmerDao.queryForId(linkItem.getFarmerId().getFarmerId()));

                            }
                            rvFarmerList.setVisibility(View.VISIBLE);
                            tvLoading.setVisibility(View.GONE);
                            farmerAdapter = new FarmerAdapter(linkItemList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            rvFarmerList.setLayoutManager(mLayoutManager);
                            rvFarmerList.setItemAnimator(new DefaultItemAnimator());
                            rvFarmerList.setAdapter(farmerAdapter);
                            rvFarmerList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
                            farmerAdapter.notifyDataSetChanged();

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        call.cancel();
                        try {
                            final Dao<Farmer, Integer> farmerDao = getHelper().getFarmerDao();
                            final Dao<LinkItem, Integer> linkitemDao = getHelper().getLinkItemDao();
                            List<LinkItem> linkItemList = linkitemDao.queryBuilder()
                                    .where()
                                    .eq("Item_id", vItem)
                                    .and()
                                    .eq("Category_id", vCategory)
                                    .query();

                            for (LinkItem linkItem : linkItemList) {
                                linkItem.setFarmerId(farmerDao.queryForId(linkItem.getFarmerId().getFarmerId()));

                            }
                            rvFarmerList.setVisibility(View.VISIBLE);
                            tvLoading.setVisibility(View.GONE);
                            farmerAdapter = new FarmerAdapter(linkItemList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            rvFarmerList.setLayoutManager(mLayoutManager);
                            rvFarmerList.setItemAnimator(new DefaultItemAnimator());
                            rvFarmerList.setAdapter(farmerAdapter);
                            rvFarmerList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
                            farmerAdapter.notifyDataSetChanged();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        Toast.makeText(getActivity(), getResources().getString(R.string.query_msg1), Toast.LENGTH_SHORT).show();


                    }
                });

            }
        });
        fbSearchAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rvFarmerList.setVisibility(View.GONE);
                tvLoading.setVisibility(View.VISIBLE);
                Call call = apiInterface.getData();
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {


                       // Log.d("TAG", response.code() + "");


                        ArrayList<Farmer> resources = (ArrayList<Farmer>) response.body();

                        try {
                            final Dao<Farmer, Integer> farmerDao = getHelper().getFarmerDao();
                            final Dao<Category, Integer> categoryDao = getHelper().getCategoryDao();
                            final Dao<Item, Integer> itemDao = getHelper().getItemDao();
                            final Dao<LinkItem, Integer> linkitemDao = getHelper().getLinkItemDao();
                            final Dao<Location1, Integer> location1Dao = getHelper().getLocation1Dao();
                            for (Farmer rm : resources) {


                                Farmer auxFarmer = new Farmer();
                                Category auxCategory = new Category();
                                Item auxItem = new Item();
                                LinkItem auxLinkItem = new LinkItem();


                                if (rm.getWebsite() != null)
                                    rm.setUrl(rm.getWebsite().getUrl());
                                else
                                    rm.setUrl("No Url Available");

                                auxFarmer.setFarmerId(rm.getFarmerId());
                                auxFarmer.setZipcode(rm.getZipcode());
                                auxFarmer.setPhone1(rm.getPhone1());
                                auxFarmer.setLocation1(rm.getLocation1());
                                auxFarmer.setUrl(rm.getUrl());
                                auxFarmer.setBusiness(rm.getBusiness());
                                auxFarmer.setFarmName(rm.getFarmName());

                                if (farmerDao.queryForId(rm.getFarmerId()) == null) {
                                    location1Dao.create(rm.getLocation1());
                                    farmerDao.create(auxFarmer);

                                }
                                auxCategory.setCategoryId(rm.getCategory());
                                if (!(categoryDao.queryBuilder()
                                        .where()
                                        .eq("CategoryID", rm.getCategory())
                                        .query().size() > 0)) {
                                    categoryDao.create(auxCategory);
                                }
                                auxItem.setItemId(rm.getItem());
                                if (!(itemDao.queryBuilder()
                                        .where()
                                        .eq("ItemId", rm.getItem())
                                        .query().size() > 0)) {
                                    itemDao.create(auxItem);
                                }
                                if (!(linkitemDao.queryBuilder()
                                        .where()
                                        .eq("Item_id", rm.getItem())
                                        .and()
                                        .eq("Category_id", rm.getCategory())
                                        .and()
                                        .eq("Farmer_id", rm.getFarmerId())
                                        .query().size() > 0)) {
                                    auxLinkItem.setItemId(auxItem);
                                    auxLinkItem.setFarmerId(auxFarmer);
                                    auxLinkItem.setCategory(auxCategory);
                                    linkitemDao.create(auxLinkItem);
                                }


                            }

                            List<LinkItem> linkItemList = linkitemDao.queryForAll();

                            for (LinkItem linkItem : linkItemList) {
                                linkItem.setFarmerId(farmerDao.queryForId(linkItem.getFarmerId().getFarmerId()));

                            }
                            rvFarmerList.setVisibility(View.VISIBLE);
                            tvLoading.setVisibility(View.GONE);
                            farmerAdapter = new FarmerAdapter(linkItemList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            rvFarmerList.setLayoutManager(mLayoutManager);
                            rvFarmerList.setItemAnimator(new DefaultItemAnimator());
                            rvFarmerList.setAdapter(farmerAdapter);
                            rvFarmerList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
                            farmerAdapter.notifyDataSetChanged();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        call.cancel();

                        try {
                            final Dao<Farmer, Integer> farmerDao = getHelper().getFarmerDao();
                            final Dao<LinkItem, Integer> linkitemDao = getHelper().getLinkItemDao();

                            List<LinkItem> linkItemList = linkitemDao.queryForAll();

                            for (LinkItem linkItem : linkItemList) {
                                linkItem.setFarmerId(farmerDao.queryForId(linkItem.getFarmerId().getFarmerId()));

                            }
                            rvFarmerList.setVisibility(View.VISIBLE);
                            tvLoading.setVisibility(View.GONE);
                            farmerAdapter = new FarmerAdapter(linkItemList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            rvFarmerList.setLayoutManager(mLayoutManager);
                            rvFarmerList.setItemAnimator(new DefaultItemAnimator());
                            rvFarmerList.setAdapter(farmerAdapter);
                            rvFarmerList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
                            farmerAdapter.notifyDataSetChanged();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        Toast.makeText(getActivity(), getResources().getString(R.string.query_msg1), Toast.LENGTH_SHORT).show();


                    }
                });


            }
        });


    }

    private DataBaseHelper getHelper() {
        if (dataBaseHelper == null)
            dataBaseHelper = OpenHelperManager.getHelper(getActivity(), DataBaseHelper.class);
        return dataBaseHelper;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }
}