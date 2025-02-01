package yunabook.yunashop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import yunabook.yunashop.domain.item.Book;
import yunabook.yunashop.domain.item.Item;
import yunabook.yunashop.repository.ItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

  @Autowired
  private ItemService itemService;

  @Autowired
  private ItemRepository itemRepository;  

  @Test
  public void 상품_저장() {
    // given
    Item item = new Book();
    item.setName("상품1");
    item.setPrice(10000);
    item.setStockQuantity(10);

    // when
    itemService.saveItem(item);

    // then
    Item findItem = itemRepository.findOne(item.getId());
    assertEquals(item.getName(), findItem.getName());
  }

  @Test
  public void 상품_저장_기존_상품_업데이트() {
    // given
    Item item = new Book();
    item.setName("상품1");
    item.setPrice(10000);
    item.setStockQuantity(10);
    itemService.saveItem(item); // 상품 저장

    // when
    item.setName("상품2");
    item.setPrice(20000);
    item.setStockQuantity(20);
    itemService.saveItem(item); // 상품 업데이트

    // then
    Item findItem = itemRepository.findOne(item.getId());
    assertEquals(item.getName(), findItem.getName());
    assertEquals(item.getPrice(), findItem.getPrice());
    assertEquals(item.getStockQuantity(), findItem.getStockQuantity());
  }
}
