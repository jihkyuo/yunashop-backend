package yunabook.yunashop.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import yunabook.yunashop.api.dto.request.BookCreateRequestDto;
import yunabook.yunashop.domain.item.Book;
import yunabook.yunashop.domain.item.Item;
import yunabook.yunashop.service.ItemService;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

  private final ItemService itemService;

  @PostMapping("/items/new")
  public Long createItem(@RequestBody @Valid BookCreateRequestDto request) {
    Book book = new Book();
    book.setName(request.getName());
    book.setPrice(request.getPrice());
    book.setStockQuantity(request.getStockQuantity());
    book.setAuthor(request.getAuthor());
    book.setIsbn(request.getIsbn());
    itemService.saveItem(book);
    return book.getId();
  }

  @GetMapping("/items")
  public List<Item> findItems() {
    return itemService.findItems();
  }

  @GetMapping("/items/{itemId}")
  public Item findOne(@PathVariable Long itemId) {
    return itemService.findOne(itemId);
  }

  @PutMapping("/items/{itemId}/edit")
  public Item updateItem(@PathVariable Long itemId, @RequestBody @Valid BookCreateRequestDto request) {
    return itemService.updateItem(itemId, request);
  }
}
