package com.hana.securityinboard.global.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * 무조건 카운터역할만 수행
 */
@Getter
@Entity
public class LoginCounter {

    @Id
    private Long id;

    @Setter private Integer todayVisit;
    private Integer totalVisit;

    public void loginCount() {
        todayVisit++;
        todayVisit++;
    }

}
