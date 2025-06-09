package com.example;

import com.example.config.RabbitMQConfig;
import com.example.model.Order;
import com.example.model.pizza.Name;
import com.example.model.pizza.Pizza;
import com.example.model.pizza.Size;
import com.example.service.OvenResponseListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@SpringBootTest
@ContextConfiguration(classes = {RabbitMQConfig.class})
@Testcontainers
public class OrderServiceIntegrationTest {

    private final String RABBIT_SERVICE = "rabbitmq";
    private final String DOCKER_COMPOSE_FILE_PATH = "src/test/resources/docker-compose.yml";

    @Container
    private DockerComposeContainer composeContainer = new DockerComposeContainer(
            new File(DOCKER_COMPOSE_FILE_PATH)
    )
            .withExposedService(RABBIT_SERVICE, 5672)
            .waitingFor(
                    RABBIT_SERVICE,
                    Wait.forLogMessage(".*Server startup complete.*", 1)
            );

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    OvenResponseListener ovenResponseListener;

    @BeforeEach
    public void cleanQueues() {
        rabbitAdmin.purgeQueue("order-queue", true);
        rabbitAdmin.purgeQueue("order-completed-queue", true);
    }

    @Test
    public void testsOrderTotalPrice() throws Exception{
        Double expectedTotalPrice = 15.0;
        Order order = createOrderPizzas();

        // Send the order
        rabbitTemplate.convertAndSend("order-exchange","order-queue", order);

        // Assert a message is received
        assertTrue(ovenResponseListener.getLatch().await(30, TimeUnit.SECONDS));
        String processedOrder = ovenResponseListener.getReceivedMessage();
        // Assert total price is correct
        assertTrue(processedOrder.contains(expectedTotalPrice.toString()));
    }

    private Order createOrderPizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza(Name.DIAVOLA, Size.M));
        pizzas.add(new Pizza(Name.FRUTTI_DI_MARE, Size.L));

        return new Order(pizzas);
    }
}
