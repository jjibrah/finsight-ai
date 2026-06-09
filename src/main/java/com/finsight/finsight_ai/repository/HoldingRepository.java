// this is the only file that talks to the database layer
package com.finsight.finsight_ai.repository;

import com.finsight.finsight_ai.model.Holding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoldingRepository extends JpaRepository<Holding, Long> {
}
