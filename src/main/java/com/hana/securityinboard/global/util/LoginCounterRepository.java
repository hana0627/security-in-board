package com.hana.securityinboard.global.util;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 다른 객체생성 없이 무조건 카운터역할만 수행
 */
public interface LoginCounterRepository extends JpaRepository<LoginCounter, Long> {
}
