package br.com.devjmcn.backend_projeto_temperatura.repository;

import br.com.devjmcn.backend_projeto_temperatura.model.data.GetDataByUnit;
import br.com.devjmcn.backend_projeto_temperatura.model.data.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DataRepository extends JpaRepository<DataEntity, UUID> {
    @Query("""
            SELECT new br.com.devjmcn.backend_projeto_temperatura.model.data.GetDataByUnit(
            d.dateTime, d.refMin, d.refCur, d.refMax, d.envMin, d.envCur, d.envMax, d.user.name)
            FROM DataEntity d WHERE d.dateTime BETWEEN :start AND :end AND d.unit.id = :unitId
            ORDER BY d.dateTime DESC
            """)
    List<GetDataByUnit> getDataByUnit(
            @Param("unitId") UUID unitId,
            @Param("start") long start,
            @Param("end") long end
    );
}