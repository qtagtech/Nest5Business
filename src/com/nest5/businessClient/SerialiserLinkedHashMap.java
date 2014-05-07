package com.nest5.businessClient;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class SerialiserLinkedHashMap implements JsonSerializer<LinkedHashMap<LinkedHashMap<Registrable,Integer>,Integer>>, JsonDeserializer<LinkedHashMap<LinkedHashMap<Registrable,Integer>,Integer>>{
	@SuppressWarnings("unchecked")
	@Override
	public JsonElement serialize( //por alguna razxon, cuando es gato o algunos otros, no hola, pasa dos veces por aca, uno con el elemento Registrable bien y otro con cosas raras. ("gr"=1)
			LinkedHashMap<LinkedHashMap<Registrable, Integer>,Integer> list,
			Type type, JsonSerializationContext context) {
		Log.i("MISPRUEBAS","dentro de la clase imprime solamente");
		for(Entry<LinkedHashMap<Registrable, Integer>, Integer> actual : list.entrySet()){
        	Log.i("MISPRUEBAS",actual.toString());
        	Log.i("MISPRUEBAS","nuevo");
        	int i = 0;
        	for(Entry<Registrable,Integer> este : ((LinkedHashMap<Registrable,Integer>) actual.getKey()).entrySet()){
        		Log.i("MISPRUEBAS",String.valueOf(i)+":");
        		Log.i("MISPRUEBAS",este.getKey().getName());
        		i++;
        	}
        	
        }
		final JsonArray jsonVentas = new JsonArray();
		for(Map.Entry<LinkedHashMap<Registrable,Integer>, Integer> orden : list.entrySet()){
			Log.i("MISPRUEBAS","dentor de la clase ya cuando si debe convertir");
			Log.i("MISPRUEBAS",orden.toString());
			LinkedHashMap<Registrable, Integer> elementos = orden.getKey();
			Set<Registrable> registrables = elementos.keySet();
			JsonArray jsonVentaActual = new JsonArray();
			for(Registrable actual : registrables){//recorro registrables uno por uno y armo un objeto json que dice si es ingrediente, producto o combo y guarda el elemento tambien.
				JsonObject jsonRegistrable = new JsonObject();
				jsonRegistrable.addProperty("name", actual.getName());
				jsonRegistrable.addProperty("price", actual.getPrice());
				jsonRegistrable.addProperty("tax", actual.getTax());
				jsonRegistrable.addProperty("id", actual.getId());
				jsonRegistrable.addProperty("type", actual.getType());
				Log.i("MISPRUEBAS","Aca1");
				GsonBuilder gb = new GsonBuilder();
				Gson gson = gb.create();
				switch(actual.getType()){
				case Registrable.TYPE_INGREDIENT: 
					jsonRegistrable.add("element", gson.toJsonTree(actual.getIngredient()));
					break;
				case Registrable.TYPE_PRODUCT: 
					//aca està con el registro de que un linkedhashmap lo procesa con este mismo archivo, entonces va y se equivoca, des-registrarlo y luego volver a registrarlo?
					jsonRegistrable.add("element", gson.toJsonTree(actual.getProduct()));
					break;
				case Registrable.TYPE_COMBO: 
					jsonRegistrable.add("element", gson.toJsonTree(actual.getCombo()));
					break;	
				}
				
				Log.i("MISPRUEBAS","Aca2");
				jsonRegistrable.addProperty("quantity", elementos.get(actual));
				jsonVentaActual.add(jsonRegistrable); //agrego a la venta actual compuesta por elementos registrables con cantidad el elemento creado 
			}
			//al crear objetos de cada elemento con su cantidad [{name,price,id,tax,type,element,quantity},{}], se agrega al array general [[{...},{...},{...},{...}],[{...},{...},{...}],[{...},{...},{...}{...}]]
			JsonObject valorVentaActual = new JsonObject();
			valorVentaActual.add("venta", jsonVentaActual);
			valorVentaActual.addProperty("values", orden.getValue());
			
			jsonVentas.add(valorVentaActual);
			
		}
		
		return jsonVentas;
		
			
	}

	@Override
	public LinkedHashMap<LinkedHashMap<Registrable, Integer>,Integer> deserialize(
			JsonElement jsonElement, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		//Log.i("MISPRUEBAS","CADENA: "+jsonElement.toString());
		JsonArray ventas = jsonElement.getAsJsonArray();
		//Log.i("MISPRUEBAS","CHECKPOINT1");
		LinkedHashMap<LinkedHashMap<Registrable, Integer>,Integer> lista = new LinkedHashMap<LinkedHashMap<Registrable,Integer>,Integer>(); 
		for(JsonElement venta : (JsonArray) ventas){
			//Log.i("MISPRUEBAS","CHECKPOINT2");
			LinkedHashMap<Registrable, Integer> actual = new LinkedHashMap<Registrable, Integer>();
			JsonArray listaRegistrables = ((JsonObject) venta).get("venta").getAsJsonArray();
			for(JsonElement registrable : (JsonArray) listaRegistrables){
				//Log.i("MISPRUEBAS","CHECKPOINT3");
				JsonObject element = ((JsonObject) registrable).get("element").getAsJsonObject();
				//Log.i("MISPRUEBAS","CHECKPOINT3.1");
				GsonBuilder gsBuilder = new GsonBuilder();
				//Log.i("MISPRUEBAS","CHECKPOINT3.2");
				Gson gs = gsBuilder.create();
				//Log.i("MISPRUEBAS","CHECKPOINT3.3");
				int tipo = ((JsonObject) registrable).get("type").getAsInt();
				//Log.i("MISPRUEBAS","CHECKPOINT3.4");
				Registrable actualRegistrable = null;
				//Log.i("MISPRUEBAS","CHECKPOINT3.5");
				//Log.i("MISPRUEBAS","CHECKPOINT4");
				switch(tipo){
				case Registrable.TYPE_INGREDIENT:
					//Log.i("MISPRUEBAS","CHECKPOINT5");
					Type entityType1 = new TypeToken<Ingredient>(){}.getType();
					Ingredient ingrediente = gs.fromJson(element, entityType1);
					actualRegistrable = new Registrable(ingrediente);
					//Log.i("MISPRUEBAS","CHECKPOINT6");
					break;
				case Registrable.TYPE_PRODUCT:
					//Log.i("MISPRUEBAS","CHECKPOINT5");
					Type entityType2 = new TypeToken<Product>(){}.getType();
					Product producto = gs.fromJson(element, entityType2);
					actualRegistrable = new Registrable(producto);
					//Log.i("MISPRUEBAS","CHECKPOINT6");
					break;
				case Registrable.TYPE_COMBO:
					//Log.i("MISPRUEBAS","CHECKPOINT5");
					Type entityType3 = new TypeToken<Combo>(){}.getType();
					Combo combo = gs.fromJson(element, entityType3);
					actualRegistrable = new Registrable(combo);
					//Log.i("MISPRUEBAS","CHECKPOINT6");
					break;
				}
				//Log.i("MISPRUEBAS","CHECKPOINT7");
				int cantidad = ((JsonObject) registrable).get("quantity").getAsInt();
				//Log.i("MISPRUEBAS","CHECKPOINT8");
				actual.put(actualRegistrable, cantidad);
				
				
			}
			//Log.i("MISPRUEBAS","CHECKPOINT9");
			int valor = ((JsonObject) venta).get("values").getAsInt();
			lista.put(actual, valor);
		}
		return lista;
		
	}
	}
