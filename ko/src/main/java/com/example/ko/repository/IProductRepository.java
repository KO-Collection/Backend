package com.example.ko.repository;

import com.example.ko.dto.home.IProductSearchHome;
import com.example.ko.dto.home.ISearch;
import com.example.ko.dto.home.ISizeProduct;
import com.example.ko.model.Product;
import com.example.ko.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "SELECT p.product_id as idProduct,p.product_name as nameProduct,p.price as price,MIN(i.img_url) as img from product p join img i on p.product_id = i.product_id where p.product_name like :name GROUP BY p.product_id", nativeQuery = true)
    Page<IProductSearchHome> findAllProductHome(Pageable pageable,@Param(value = "name") String name);
    @Query(value = " SELECT p.product_id as idProduct, p.product_name as nameProduct, p.price as price, MIN(i.img_url) as img, p.create_time_product as createdTime\n" +
            "FROM product p\n" +
            "JOIN img i ON p.product_id = i.product_id\n" +
            "JOIN type_product tp ON p.type_product_id = tp.type_product_id\n" +
            "JOIN color_product cl ON p.color_product_id = cl.color_product_id\n" +
            "WHERE \n" +
            "    YEAR(p.create_time_product) like :time \n" +
            "    AND cl.color_product_name IN (:colorList) \n" +
            "    AND p.price BETWEEN :min_price AND :max_price \n" +
            "    And tp.type_product_name IN (:type_list) \n" +
            "GROUP BY p.product_id\n" +
            "ORDER BY p.create_time_product DESC \n ", nativeQuery = true)
    List<IProductSearchHome> findAllProduct( @Param(value = "time")String time, @Param(value = "colorList") String[] colorList, @Param(value = "min_price") Double minPrice,@Param(value = "max_price") Double maxPrice,@Param(value = "type_list") String[] typeList);

    @Query(value = "SELECT p.product_id as idProduct,p.product_name as nameProduct,p.price as price,MIN(i.img_url) as img from product p join img i on p.product_id = i.product_id where p.product_name like :name GROUP BY p.product_id", nativeQuery = true)
    Page<IProductSearchHome> findAllProductHomePrice(Pageable pageable,@Param(value = "name") String name);
    @Query(value = "SELECT p.product_id as idProduct,p.product_name as nameProduct,p.price as price," +
            "MIN(i.img_url) as img,sum(o.quantity_order) as quantity_total \n" +
            "from product p\n" +
            "join img i on p.product_id = i.product_id \n" +
            "join order_detail o on p.product_id = o.product_id\n" +
            "GROUP BY p.product_id\n" +
            "ORDER BY SUM(o.quantity_order) DESC \n" +
            "limit 10;",nativeQuery = true)
    List<IProductSearchHome> getBestSeller();
    @Query(value = "SELECT p.product_id as idProduct,p.product_name as nameProduct," +
            "p.price as price,MIN(i.img_url) as img,p.create_time_product as createdTime \n" +
            "from product p\n" +
            "join img i on p.product_id = i.product_id \n" +
            "WHERE YEAR(p.create_time_product) = YEAR(CURDATE()) " +
            "GROUP BY p.product_id\n" +
            "ORDER BY p.create_time_product DESC \n" +
            "limit 10;",nativeQuery = true)
    List<IProductSearchHome> getProductNew();
    @Query(value = "SELECT p.product_id as idProduct,p.product_name as nameProduct,p.price as price,MIN(i.img_url) as img from product p join img i on p.product_id = i.product_id where p.product_id = :product_id GROUP BY p.product_id", nativeQuery = true)
    IProductSearchHome getProductByIdProductForCart(@Param(value = "product_id") Long idProduct);
    @Query(value = "SELECT * FROM ko_collection.product WHERE product_id = :product_id", nativeQuery = true)
    Product getProductsById(@Param(value = "product_id") Long idProduct);
    @Query(value = "select s.size_name as name ,s.size_id as id from size_detail dt \n" +
            "join size s on dt.size_id = s.size_id WHERE dt.product_id = :product_id", nativeQuery = true)
    List<ISizeProduct> getSizeProduct(@Param(value = "product_id") Long idProduct);
    @Query(value = "  select color_product_id as idColor,color_product_name as nameColor from color_product ", nativeQuery = true)
    List<ISearch> getColorProduct();
    @Query(value = "  select size_id as idSize,size_name as nameSize from size ", nativeQuery = true)
    List<ISearch> getSizeSearch();
    @Query(value = " select type_detail_id as idType,type_product_name as nameType from type_product ", nativeQuery = true)
    List<ISearch> getTypeSearch();

}
