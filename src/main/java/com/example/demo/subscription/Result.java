package com.example.demo.subscription;

import io.vavr.control.Either;

import java.util.List;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Collections.emptyList;

public class Result {

    final Either<Failure, Success> result;

    private Result(Either<Failure, Success> result) {
        this.result = result;
    }

    public static Result success() {
        return success(emptyList());
    }

    public static Result success(DomainEvent event){
        return new Result(right(new Success(List.of(event))));
    }

    public static Result success(DomainEvent... events){
        return new Result(right(new Success(List.of(events))));
    }

    public static Result success(List<DomainEvent> events){
        return new Result(right(new Success(events)));
    }

    public static Result failure(String reason){
        return new Result(left(new Failure(reason)));
    }

    public boolean isFailure() {
        return result.isLeft();
    }

    public boolean isSuccessful() {
        return result.isRight();
    }

    public String reason(){
        if (result.isLeft()){
           return result.getLeft().reason();
        }
        return "OK";
    }

    public List<DomainEvent> events(){
        return result
                .map(success -> success.events())
                .getOrElse(emptyList());
    }
}
