package com.triple.o.labs.imageAnalizer.daos;

import com.triple.o.labs.imageAnalizer.entities.Snapshot;
import org.springframework.data.repository.CrudRepository;

public interface SnapshotDao extends CrudRepository<Snapshot, Long> {
}
