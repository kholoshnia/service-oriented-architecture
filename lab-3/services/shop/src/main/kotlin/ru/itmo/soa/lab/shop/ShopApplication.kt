package ru.itmo.soa.lab.shop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
//import org.springframework.cloud.netflix.ribbon.RibbonClient

@RibbonClient(name = "os-shop-service")
@EnableEurekaClient
@SpringBootApplication
//@LoadBalancerClient(name = "os-shop-service")
class ShopApplication

fun main(args: Array<String>) {
    runApplication<ShopApplication>(*args)
}
