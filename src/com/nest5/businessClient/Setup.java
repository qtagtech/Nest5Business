/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.nest5.businessClient;

/**
 * Class to be customized with app-specific data. The Eclipse plugin will set
 * these values when the project is created.
 */
public class Setup {

    /**
     * The AppEngine app name, used to construct the production service URL
     * below.
     */
    private static final String APP_NAME = "Nest5 Business";
    
    //Start Database Variables
    
    //Common
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_OWN_SYNC_ID = "sync_id";
    public static final String COMPANY_ID = "nest5_logged_id";
    public static final String COMPANY_NAME = "nest5_company_name";
    public static final String LOGGED_IN = "is_company_logged_in_nest5";
    public static final String DEVICE_REGISTERED = "is_device_registered_in_nest5_big_data_server";
    public static final String DEVICE_REGISTERED_ID = "id_registered_in_nest5_big_data_server";
    public static final String NEW_INSTALL = "is_new_install";
    public static final String IS_UPDATING = "updating_database";


    
    //Ingredient Category Table
    public static final String TABLE_CATEGORY_INGREDIENTS = "ingredient_category";
    
    
  //Product Category Table
    public static final String TABLE_CATEGORY_PRODUCTS = "product_category";
    
    
    //Ingredient
    public static final String TABLE_INGREDIENTS = "ingredient";
    public static final String COLUMN_INGREDIENT_CATEGORY_ID = "category_id";
    public static final String COLUMN_INGREDIENT_TAX_ID = "tax_id";
    public static final String COLUMN_INGREDIENT_UNIT = "unit_id";
    public static final String COLUMN_INGREDIENT_COST_PER_UNIT = "cost_per_unit";
    public static final String COLUMN_INGREDIENT_PRICE_PER_UNIT = "price_per_unit";
    public static final String COLUMN_INGREDIENT_PRICE_MEASURE = "price_measure";
    public static final String COLUMN_INGREDIENT_QTY = "quantity";
    public static final String COLUMN_INGREDIENT_DATE = "date";
    
    
    //Product costo automatico? costo precio getingredientes()
    public static final String TABLE_PRODUCTS = "product";
    public static final String COLUMN_PRODUCT_CATEGORY_ID = "category_id";
    public static final String COLUMN_PRODUCT_AUTOMATIC = "automatic_cost";
    public static final String COLUMN_PRODUCT_COST = "cost";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_TAX_ID = "tax_id";
    
  //Combo costo automatico? costo precio getingredientes()
    public static final String TABLE_COMBOS = "combo";
    //public static final String COLUMN_COMBO_CATEGORY_ID = "category_id";
    public static final String COLUMN_COMBO_AUTOMATIC = "automatic_cost";
    public static final String COLUMN_COMBO_COST = "cost";
    public static final String COLUMN_COMBO_PRICE = "price";
    public static final String COLUMN_COMBO_TAX_ID = "tax_id";
    
    //ProductoIngrediente tabla que relaciona productos con ingredientes, un producto puede tener muchos ingredientes y vicebersa
    public static final String TABLE_PRODUCTINGREDIENT = "productingredient";
    public static final String COLUMN_PRODUCTINGREDIENT_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCTINGREDIENT_INGREDIENT_ID = "ingredient_id";
    public static final String COLUMN_PRODUCTINGREDIENT_QUANTITY = "qty";
    
  //ComboIngrediente tabla que relaciona productos con ingredientes, un producto puede tener muchos ingredientes y vicebersa
    public static final String TABLE_COMBOINGREDIENT = "comboingredient";
    public static final String COLUMN_COMBOINGREDIENT_COMBO_ID = "combo_id";
    public static final String COLUMN_COMBOINGREDIENT_INGREDIENT_ID = "ingredient_id";
    public static final String COLUMN_COMBOINGREDIENT_QUANTITY = "qty";
    
  //ComboProducto tabla que relaciona productos con ingredientes, un producto puede tener muchos ingredientes y vicebersa
    public static final String TABLE_COMBOPRODUCT = "comboproduct";
    public static final String COLUMN_COMBOPRODUCT_COMBO_ID = "combo_id";
    public static final String COLUMN_COMBOPRODUCT_PRODUCT_ID = "product_id";
    public static final String COLUMN_COMBOPRODUCT_QUANTITY = "qty";
    
    //Tax
    public static final String TABLE_TAX = "tax";
    public static final String COLUMN_TAX_PERCENTAGE = "percentage";
    
  //Unit tabla que contiene las unidades de medida de los ingredientes
    public static final String TABLE_UNIT = "measurement_unit";
    public static final String COLUMN_UNIT_INITIALS = "initials";
    public static final String COLUMN_UNIT_MULTIPLIERS = "multipliers";
    
  //Sale tabla que contiene las ventas
    public static final String TABLE_SALE = "sale";
    public static final String COLUMN_SALE_DATE = "date";
    public static final String COLUMN_SALE_RECEIVED = "received";
    public static final String COLUMN_SALE_METHOD = "payment_method";
    public static final String COLUMN_SALE_ISDELIVERY = "delivery";
    public static final String COLUMN_SALE_ISTOGO = "togo";
    public static final String COLUMN_SALE_TIP = "tip";
    public static final String COLUMN_SALE_DISCOUNT = "discount";

    
    //SaleItem  guarda las propiedades de un registrable con una cantidad en la base de datos
    public static final String TABLE_SALE_ITEM = "sale_item";
    public static final String COLUMN_SALE_ID = "sale_id";
    public static final String COLUMN_SALE_ITEM_TYPE = "sale_item_type";
    public static final String COLUMN_SALE_ITEM_ID = "sale_item_id";
    public static final String COLUMN_SALE_ITEM_QTY = "sale_item_qty";
    
    
  //SyncRow  guarda las propiedades de una fila que se debe actualizar
    public static final String TABLE_SYNC_ROW = "sync_row"; 
    public static final String COLUMN_DEVICE_ID = "device_id";
    public static final String COLUMN_TABLE_NAME = "reference";
    public static final String COLUMN_ROW_ID = "row_id";
    public static final String COLUMN_TIME_CREATED = "time_created";
    public static final String COLUMN_SYNC_ID = "sync_id";
    public static final String COLUMN_FIELDS = "fields";
    
    //
    
    //END Database Variables
    

    /**
     * The URL of the production service.
     */
   // public static final String PROD_URL = "http://www.nest5.com";
    public static final String PROD_URL = "http://192.168.11.103:8092";
    //public static final String PROD_URL = "http://api.nest5.com/";
    public static final String PROD_BIGDATA_URL = "http://192.168.11.103:8090/Nest5BusinessData";
    //public static final String PROD_BIGDATA_URL = "http://bigdata.nest5.com";
    

    /**
     * The C2DM sender ID for the server. A C2DM registration with this name
     * must exist for the app to function correctly.
     */
    public static final String SENDER_ID = "juanda6@gmail.com";
    
    public static final String apiKey = "zAxEE9U1691Nq0h5JiJ0X20tcayF5RTpmzIOctVGNQNBByHslznDR0VP7rWOuyW";
    
    //Íconos
    public static final int ICON_WIN_STAR = 99;
    public static final int ICON_FACE_WIN = 0;
    public static final int ICON_FACE_ONE = 1;
    public static final int ICON_FACE_TWO = 2;
    public static final int ICON_FACE_THREE = 3;
    public static final int ICON_FACE_CHEER = 4;
    
    
    
}
