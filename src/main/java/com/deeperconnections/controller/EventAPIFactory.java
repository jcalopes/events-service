package com.deeperconnections.controller;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.ResourceResolver;
import jakarta.inject.Singleton;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/*
Class to bind data fetchers with domain generating a graphql api.
 */
@Factory
@Slf4j
public class EventAPIFactory {

    @Bean
    @Singleton
    public GraphQL graphQL(ResourceResolver resourceResolver, DataFetchers graphQLDataFetchers) {
        SchemaParser schemaParser = new SchemaParser();

        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        // Load schema
        Optional<InputStream> graphqlSchema = resourceResolver.getResourceAsStream("classpath:schema.graphqls");

        if (graphqlSchema.isPresent()) {
            typeRegistry.merge(schemaParser.parse(new BufferedReader(new InputStreamReader(graphqlSchema.get()))));

            RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                    .type(newTypeWiring("Query")
                            .dataFetcher("events", graphQLDataFetchers.getAllEvents())
                            .dataFetcher("eventByLocal", graphQLDataFetchers.getEventsByLocal()))
                    .type(newTypeWiring("Mutation").dataFetcher("createEvent", graphQLDataFetchers.createEvent()))
                    .build();

            SchemaGenerator schemaGenerator = new SchemaGenerator();
            GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

            return GraphQL.newGraphQL(graphQLSchema).build();

        } else {
            log.debug("No GraphQL services found, returning empty schema");
            return new GraphQL.Builder(GraphQLSchema.newSchema().build()).build();
        }
    }
}
