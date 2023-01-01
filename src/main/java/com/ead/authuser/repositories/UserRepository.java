package com.ead.authuser.repositories;

import com.ead.authuser.models.QUserModel;
import com.ead.authuser.models.UserModel;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends MongoRepository<UserModel, String>, QuerydslPredicateExecutor<UserModel>,
        QuerydslBinderCustomizer<QUserModel> {

    Optional<UserModel> findById(String id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    @Override
    default void customize(QuerydslBindings bindings, QUserModel root) {
        bindings.bind(root.username).first(StringExpression::contains);
        bindings.bind(root.email).first(StringExpression::contains);
        bindings.bind(root.fullname).first(StringExpression::contains);
        bindings.bind(String.class)
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));
        bindings.excluding(root.password);
    }

}

