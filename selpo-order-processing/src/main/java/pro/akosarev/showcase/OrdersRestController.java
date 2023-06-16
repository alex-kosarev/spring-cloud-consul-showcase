package pro.akosarev.showcase;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/order-processing/orders")
public class OrdersRestController implements RowMapper<OrdersRestController.OrderItem> {

    private final RestTemplate restTemplate;

    private final JdbcTemplate jdbcTemplate;

    public OrdersRestController(RestTemplate restTemplate, JdbcTemplate jdbcTemplate) {
        this.restTemplate = restTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<OrderItem> getOrderItems() {
        return this.jdbcTemplate.query("select * from t_order_item", this);
    }

    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        int productId = rs.getInt("id_product");
        Product product = this.restTemplate.getForObject("http://selpo-cat/api/catalogue/products/" + productId, Product.class);
        return new OrderItem(rs.getInt("id"), rs.getInt("c_amount"),
                product);
    }

    record OrderItem(int id, int amount, Product product) {
    }

    record Product(int id, String title) {
    }
}
