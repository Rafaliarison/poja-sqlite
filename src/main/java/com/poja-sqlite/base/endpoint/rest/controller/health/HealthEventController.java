package com.poja-sqlite.base.endpoint.rest.controller.health;

import com.poja-sqlite.base.PojaGenerated;
import com.poja-sqlite.base.endpoint.event.EventProducer;
import com.poja-sqlite.base.endpoint.event.gen.UuidCreated;
import com.poja-sqlite.base.repository.DummyUuidRepository;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.poja-sqlite.base.endpoint.rest.controller.health.PingController.KO;
import static com.poja-sqlite.base.endpoint.rest.controller.health.PingController.OK;
import static java.util.UUID.randomUUID;

@PojaGenerated
@RestController
@AllArgsConstructor
public class HealthEventController {

  DummyUuidRepository dummyUuidRepository;
  EventProducer eventProducer;

  @GetMapping(value = "/health/event")
  public ResponseEntity<String> random_uuid_is_fired_then_created() throws InterruptedException {
    var randomUuid = randomUUID().toString();
    var event = new UuidCreated().toBuilder().uuid(randomUuid).build();

    eventProducer.accept(List.of(event));

    Thread.sleep(20_000);
    return dummyUuidRepository.findById(randomUuid).map(dummyUuid -> OK).orElse(KO);
  }
}
