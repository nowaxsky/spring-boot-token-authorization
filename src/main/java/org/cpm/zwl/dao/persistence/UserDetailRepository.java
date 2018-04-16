package org.cpm.zwl.dao.persistence;

import org.cpm.zwl.dao.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

  public UserDetail findByUserId(String userId);
}
