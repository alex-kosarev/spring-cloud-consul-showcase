package pro.akosarev.showcase;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/catalogue/products")
public class ProductsRestController implements RowMapper<ProductsRestController.Product> {

    private final JdbcTemplate jdbcTemplate;

    public ProductsRestController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(rs.getInt("id"), rs.getString("c_title"));
    }

    record Product(int id, String title) {
    }

    @GetMapping
    public List<Product> getProducts() {
        return this.jdbcTemplate.query("select * from t_product", this);
    }

    @GetMapping("{productId}")
    public Product getProducts(@PathVariable int productId) {
        return this.jdbcTemplate.queryForObject("select * from t_product where id = ?", this, productId);
    }
}
