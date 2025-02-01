package yunabook.yunashop.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import yunabook.yunashop.api.dto.request.OrderCreateRequestDto;
import yunabook.yunashop.api.dto.response.OrderResponseDto;
import yunabook.yunashop.repository.OrderSearch;
import yunabook.yunashop.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

  private final OrderService orderService;

  @PostMapping("/order")
  public Long order(@RequestBody @Valid OrderCreateRequestDto request) {
    return orderService.order(request.getMemberId(), request.getItemId(), request.getCount());
  }

  @GetMapping("/orders")
  public List<OrderResponseDto> orderList(OrderSearch orderSearch) {
    return orderService.findOrders(orderSearch).stream()
        .map(OrderResponseDto::new)
        .collect(Collectors.toList());
  }

  @PostMapping("/orders/{orderId}/cancel")
  public Long cancelOrder(@PathVariable("orderId") Long orderId) {
    orderService.cancelOrder(orderId);
    return orderId;
  }
}
