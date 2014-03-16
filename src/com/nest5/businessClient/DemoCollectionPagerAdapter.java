package com.nest5.businessClient;

import java.util.List;

import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



//Since this is an object collection, use a FragmentStatePagerAdapter,
//and NOT a FragmentPagerAdapter.
public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
	
	//private List<IngredientCategory> ingredientCategoryList;
	private Fragment fragment;
	
	public DemoCollectionPagerAdapter(FragmentManager fm) {
     super(fm);
     //ingredientCategoryList = ingredients;
 }

 @Override
 public Fragment getItem(int i) {
	 Bundle args = new Bundle();
	 //se hace un case y dependiento de la posicion se coge un demoobjectfragmet, el cual tienen el layout de la pagina a mostrar
	 switch(i)
	 {
	 case 0:
	 {
		 //Fragment fragment1 = new HomeObjectFragment(ingredientCategoryList);
		 Fragment fragment1 = new HomeObjectFragment();
		 args.putInt(HomeObjectFragment.ARG_OBJECT, i + 1);
		 fragment1.setArguments(args);
		 fragment = fragment1;
		 break;
	 }
	 case 1:
	 {
		 Fragment fragment2 = new SalesObjectFragment();
		 args.putInt(SalesObjectFragment.ARG_OBJECT, i + 1);
		 fragment2.setArguments(args);
		 fragment = fragment2;
		 break;
	 }
	 case 2:
	 {
		 /*Fragment fragment3 = new DailyObjectFragment();
		 args.putInt(DailyObjectFragment.ARG_OBJECT, i + 1);
		 fragment3.setArguments(args);
		 fragment = fragment3;
		 break;*/
		 Fragment fragment5 = new Nest5ReadObjectFragment();
		 args.putInt(Nest5ReadObjectFragment.ARG_OBJECT, i + 1);
		 fragment5.setArguments(args);
		 fragment = fragment5;
		 break;
	 }
	/* case 3:
	 {
		 Fragment fragment4 = new InventoryObjectFragment();
		 args.putInt(DailyObjectFragment.ARG_OBJECT, i + 1);
		 fragment4.setArguments(args);
		 fragment = fragment4;
		 break;
	 }
	 case 4:
	 {
		 Fragment fragment5 = new Nest5ReadObjectFragment();
		 args.putInt(Nest5ReadObjectFragment.ARG_OBJECT, i + 1);
		 fragment5.setArguments(args);
		 fragment = fragment5;
		 break;
	 }*/
	 }
      
     
     // Our object is just an integer :-P
     
     
     
     return fragment;
 }

 @Override
 public int getCount() {
	 //numero de paginas que hay
     return 3;
 }

 @Override
 public CharSequence getPageTitle(int position) {
     CharSequence title = "OBJECT";
	 switch(position)
	 {
	 case 0: title =  "INICIO - PUNTO DE VENTA Nest5";
		 break;
	 case 1: title =  "DETALLE DE ÓRDENES";
	 break;
	/* case 2: title =  "DETALLE DE REGISTROS";
	 break;
	 case 3: title =  "DETALLE DE INVENTARIOS";
	 break;*/
	 case 2: title =  "REGISTRO DE USUARIOS Y LECTURA DE TARJETAS NEST5";
	 break;
	 }
	 return title;
 }
}


