package com.nest5.businessClient;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.nest5.businessClient.SaleProducto;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table SALE_PRODUCTO.
*/
public class SaleProductoDao extends AbstractDao<SaleProducto, Void> {

    public static final String TABLENAME = "SALE_PRODUCTO";

    /**
     * Properties of entity SaleProducto.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Quantity = new Property(0, Integer.class, "quantity", false, "QUANTITY");
        public final static Property SaleId = new Property(1, Long.class, "saleId", false, "SALE_ID");
        public final static Property ProductoId = new Property(2, Long.class, "productoId", false, "PRODUCTO_ID");
    };

    private DaoSession daoSession;

    private Query<SaleProducto> dailySale_ProductsQuery;

    public SaleProductoDao(DaoConfig config) {
        super(config);
    }
    
    public SaleProductoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'SALE_PRODUCTO' (" + //
                "'QUANTITY' INTEGER," + // 0: quantity
                "'SALE_ID' INTEGER," + // 1: saleId
                "'PRODUCTO_ID' INTEGER);"); // 2: productoId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'SALE_PRODUCTO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SaleProducto entity) {
        stmt.clearBindings();
 
        Integer quantity = entity.getQuantity();
        if (quantity != null) {
            stmt.bindLong(1, quantity);
        }
 
        Long saleId = entity.getSaleId();
        if (saleId != null) {
            stmt.bindLong(2, saleId);
        }
 
        Long productoId = entity.getProductoId();
        if (productoId != null) {
            stmt.bindLong(3, productoId);
        }
    }

    @Override
    protected void attachEntity(SaleProducto entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public SaleProducto readEntity(Cursor cursor, int offset) {
        SaleProducto entity = new SaleProducto( //
            cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // quantity
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // saleId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // productoId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SaleProducto entity, int offset) {
        entity.setQuantity(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setSaleId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setProductoId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(SaleProducto entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(SaleProducto entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "products" to-many relationship of DailySale. */
    public List<SaleProducto> _queryDailySale_Products(Long productoId) {
        synchronized (this) {
            if (dailySale_ProductsQuery == null) {
                QueryBuilder<SaleProducto> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.ProductoId.eq(null));
                dailySale_ProductsQuery = queryBuilder.build();
            }
        }
        Query<SaleProducto> query = dailySale_ProductsQuery.forCurrentThread();
        query.setParameter(0, productoId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getDailySaleDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getProductoDao().getAllColumns());
            builder.append(" FROM SALE_PRODUCTO T");
            builder.append(" LEFT JOIN DAILY_SALE T0 ON T.'SALE_ID'=T0.'_id'");
            builder.append(" LEFT JOIN PRODUCTO T1 ON T.'PRODUCTO_ID'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected SaleProducto loadCurrentDeep(Cursor cursor, boolean lock) {
        SaleProducto entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        DailySale dailySale = loadCurrentOther(daoSession.getDailySaleDao(), cursor, offset);
        entity.setDailySale(dailySale);
        offset += daoSession.getDailySaleDao().getAllColumns().length;

        Producto producto = loadCurrentOther(daoSession.getProductoDao(), cursor, offset);
        entity.setProducto(producto);

        return entity;    
    }

    public SaleProducto loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<SaleProducto> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<SaleProducto> list = new ArrayList<SaleProducto>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<SaleProducto> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<SaleProducto> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}