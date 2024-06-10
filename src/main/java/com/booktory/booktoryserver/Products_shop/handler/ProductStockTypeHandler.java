package com.booktory.booktoryserver.Products_shop.handler;

import com.booktory.booktoryserver.Products_shop.constant.ProductStock;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

@MappedTypes(ProductStock.class)
public class ProductStockTypeHandler implements TypeHandler<ProductStock> {

    @Override
    public void setParameter(PreparedStatement ps, int i, ProductStock parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getKey());
    }

    @Override
    public ProductStock getResult(ResultSet rs, String columnName) throws SQLException {
        String stockKey = rs.getString(columnName);
        return getProductStock(stockKey);
    }

    @Override
    public ProductStock getResult(ResultSet rs, int columnIndex) throws SQLException {
        String stockKey = rs.getString(columnIndex);
        return getProductStock(stockKey);
    }

    @Override
    public ProductStock getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String stockKey = cs.getString(columnIndex);
        return getProductStock(stockKey);
    }

    private ProductStock getProductStock(String roleKey){
        ProductStock productStock = null;
        switch (roleKey){
            case "IN_STOCK":
                productStock = ProductStock.IN_STOCK;
                break;
            case "OUT_OF_STOCK":
                productStock = ProductStock.OUT_OF_STOCK;
                break;
            case "LOW_STOCK":
                productStock = ProductStock.LOW_STOCK;
                break;
            case "BACK_ORDER":
                productStock = ProductStock.BACK_ORDER;
                break;
            case "DISCONTINUED":
                productStock = ProductStock.DISCONTINUED;
                break;
            default:
                productStock = ProductStock.IN_STOCK;
                break;
        }

      return productStock;
    }
}
