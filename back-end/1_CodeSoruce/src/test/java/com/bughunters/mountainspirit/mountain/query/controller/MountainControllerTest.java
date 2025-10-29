package com.bughunters.mountainspirit.mountain.query.controller;

import com.bughunters.mountainspirit.mountain.command.entity.Mountain;
import com.bughunters.mountainspirit.mountain.query.dto.MountainDTO;
import com.bughunters.mountainspirit.mountain.query.service.MountainSearchService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest(MountainController.class)

@SpringBootTest
@Transactional
class MountainControllerTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void getSearchMountain() {
        Mountain mountain = new Mountain();
        mountain.setId("0000000110");
        mountain.setFrtrlNm("가야산");
        mountain.setCtpvNm("경기도");
        mountain.setAddrNm("수원시");
        mountain.setLat(35.01);
        mountain.setLot(127.01);
        mountain.setAslAltide(1561D);
        mountain.setCrtrDt(LocalDateTime.now());
        mountain.setMtnCd(123123123L);
        mountain.setStampPath(null);
        mountain.setStampUpdateDate(null);
        entityManager.persist(mountain);
        entityManager.flush();

        String jpql = "select m from Mountain m where m.frtrlNm like '%야%'";
        List<Mountain> foundMountainList = entityManager.createQuery(jpql, Mountain.class).getResultList();

        Assertions.assertFalse(foundMountainList.isEmpty());
        Assertions.assertTrue(foundMountainList.stream().anyMatch(m -> m.getFrtrlNm().contains("야")));
    }
}