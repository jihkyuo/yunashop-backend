package yunabook.yunashop.domain.item;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import yunabook.yunashop.domain.Category;
import yunabook.yunashop.exception.NotEnoughStockException;

@BatchSize(size = 100) // ToOne 관계는 어노테이션을 class에
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>();

  private String name;
  private int price;
  private int stockQuantity;

  // ===== 비즈니스 로직 =====
  // memo: 데이터를 가지고 있는 쪽에 비즈니스 로직을 넣는다. => 응집도가 높아진다.

  /**
   * stock 증가
   */
  public void addStock(int quantity) {
    this.stockQuantity += quantity;
  }

  /**
   * stock 감소
   */
  public void removeStock(int quantity) {
    int restStock = this.stockQuantity - quantity;
    if (restStock < 0) {
      throw new NotEnoughStockException("need more stock");
    }
    this.stockQuantity = restStock;
  }
}
