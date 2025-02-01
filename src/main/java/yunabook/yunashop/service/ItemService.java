package yunabook.yunashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import yunabook.yunashop.api.dto.request.BookCreateRequestDto;
import yunabook.yunashop.domain.item.Book;
import yunabook.yunashop.domain.item.Item;
import yunabook.yunashop.repository.ItemRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  /**
   * 상품 저장
   */
  public void saveItem(Item item) {
    itemRepository.save(item);
  }

  /**
   * 상품 수정
   */
  public Item updateItem(Long itemId, BookCreateRequestDto request) {
    Book findItem = (Book) itemRepository.findOne(itemId); // ! findItem으로 찾아왔기 때문에 영속 상태가 되므로, 변경감지가 동작한다.
    findItem.setName(request.getName());
    findItem.setPrice(request.getPrice());
    findItem.setStockQuantity(request.getStockQuantity());
    // 책만 처리할 수 있는 메서드가 되었는데, 이게 맞나?
    findItem.setAuthor(request.getAuthor());
    findItem.setIsbn(request.getIsbn());
    return findItem;
  }

  /**
   * 상품 단건 조회
   * */ 
  @Transactional(readOnly = true)
  public Item findOne(Long itemId) {
    return itemRepository.findOne(itemId);
  }

  /**
   * 상품 전체 조회
   */
  @Transactional(readOnly = true)
  public List<Item> findItems() {
    return itemRepository.findAll();
  }
}
