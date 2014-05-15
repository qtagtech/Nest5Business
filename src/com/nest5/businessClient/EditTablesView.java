package com.nest5.businessClient;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.Spinner;

public class EditTablesView extends DialogFragment {
	
	public interface OnCreateComboListener {
        //public void OnComboCreated(Boolean status,LinkedHashMap<Ingredient,Double> ingredents,LinkedHashMap<Product,Double> products,String name,Double cost,Double price,Tax tax); //manda datos de combo para crearlo
        
    }
	String[] DayOfWeek = {"Sunday", "Monday", "Tuesday", 
			"Wednesday", "Thursday", "Friday", "Saturday"};
	
//private OnCreateComboListener onCreateComboListener;
private DialogFragment frag;
private Context mContext;
private Button saveBtn;
private Button cancelBtn;
private int curType;
private LayoutInflater inflater;




@Override
public void onAttach(Activity activity){
	super.onAttach(activity);
	try
	{
		//onCreateComboListener = (OnCreateComboListener) activity;
	}
	catch(ClassCastException e){
		//throw new ClassCastException(activity.toString() + " must implement OnCreateComboListener");
	}
}


 public EditTablesView() {
     // Empty constructor required for DialogFragment
 }
 



@Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
	 frag = this;
	 this.inflater = inflater;
     View view = inflater.inflate(R.layout.tables_layout, container);
 	getDialog().setTitle("¿Cómo se distribuye tu Negocio?");
 	mContext = view.getContext();
 	final Button btnOpenPopup = (Button) view.findViewById(R.id.openpopup);
 	final View popupView = inflater.inflate(R.layout.popup, null);
	btnOpenPopup.setOnClickListener(new Button.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			
			final PopupWindow popupWindow = new PopupWindow(
					popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
			Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
			
			Spinner popupSpinner = (Spinner)popupView.findViewById(R.id.popupspinner);
			
			ArrayAdapter<String> adapter = 
					new ArrayAdapter<String>(mContext, 
							android.R.layout.simple_spinner_item, DayOfWeek);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			popupSpinner.setAdapter(adapter);
			
			btnDismiss.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View v) {
					popupWindow.dismiss();
				}});
			
			popupWindow.showAsDropDown(btnOpenPopup, 50, -30);
			
			popupView.setOnTouchListener(new OnTouchListener() {
				int orgX, orgY;
				int offsetX, offsetY;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						orgX = (int) event.getX();
						orgY = (int) event.getY();
						break;
					case MotionEvent.ACTION_MOVE:
						offsetX = (int)event.getRawX() - orgX;
						offsetY = (int)event.getRawY() - orgY;
						popupWindow.update(offsetX, offsetY, -1, -1, true);
						break;
					}
					return true;
				}});
		}

	});
 	
 	
 	
 	
 	
 	cancelBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			frag.dismiss();
			
		}
	});
 	
saveBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			frag.dismiss();	
		}
	});

     return view;
 }





private OnTouchListener touchListener = new OnTouchListener() {
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		      ClipData data = ClipData.newPlainText("", "");
		      DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
		      v.startDrag(data, shadowBuilder, v, 0);
		      v.setVisibility(View.INVISIBLE);
		      //Toast.makeText(mContext, String.valueOf(v.getId()), Toast.LENGTH_LONG).show();
		      return true;
		    } else {
		    return false;
		    }
	}
}; 

private OnDragListener dragListener = new OnDragListener() {
	
	@Override
	public boolean onDrag(View v, DragEvent event) {
		int action = event.getAction();
	    switch (event.getAction()) {
	    case DragEvent.ACTION_DRAG_STARTED:
	    // Do nothing
	      break;
	    case DragEvent.ACTION_DRAG_ENTERED:
	      v.setAlpha((float) 0.5);
	      break;
	    case DragEvent.ACTION_DRAG_EXITED:        
	    	v.setAlpha((float) 1);
	      break;
	    case DragEvent.ACTION_DROP:
	      // Dropped, reassign View to ViewGroup
	      View view = (View) event.getLocalState();
	      ViewGroup owner = (ViewGroup) view.getParent();
	      owner.removeView(view);
	      ScrollView container = (ScrollView) v;
	      LinearLayout layout = (LinearLayout) v.findViewById(R.id.ingredient_shelve);
	      view.setVisibility(View.VISIBLE);
	      break;
	    case DragEvent.ACTION_DRAG_ENDED:
	    	v.setAlpha((float) 1);
	      default:
	      break;
	    }
	    return true;
	}
};
}