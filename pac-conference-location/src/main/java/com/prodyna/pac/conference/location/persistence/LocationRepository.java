package com.prodyna.pac.conference.location.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * Repository to access Neo4j for Locations.
 */
public interface LocationRepository extends Neo4jRepository<LocationEntity, Long> {
}
