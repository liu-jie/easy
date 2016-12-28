package com.eirture.easy.base.bus;

/**
 * Created by eirture on 16-12-28.
 */

import com.eirture.easy.base.functions.Action;
import com.eirture.easy.base.functions.ActionVoid;

public class Result<T> {
    private Action<T> successFunction;
    private Action errorFunction;
    private ActionVoid preludeFunction;
    private ActionVoid finalityFunction;

    private Result() {
    }

    public Result<T> prelude(ActionVoid action) {
        this.preludeFunction = action;
        return this;
    }

    public Result<T> finality(ActionVoid action) {
        this.finalityFunction = action;
        return this;
    }

    public Result<T> successFunction(Action<T> successFunction) {
        this.successFunction = successFunction;
        return this;
    }

    public Result<T> errorFunction(Action<Error> errorFunction) {
        this.errorFunction = errorFunction;
        return this;
    }

    public void result(DataEvent<T> dataEvent) {
        if (dataEvent == null) {
            System.err.println("result: dataEvent == null");
            return;
        }

        if (dataEvent.isPrelude()) {
            if (preludeFunction != null)
                preludeFunction.action();
            return;
        }
        if (dataEvent.successful()) {
            if (successFunction != null)
                successFunction.action(dataEvent.data);
        } else {
            if (errorFunction != null)
                errorFunction.action(dataEvent.error);
        }

        if (finalityFunction != null)
            finalityFunction.action();

    }

    public static <T> Result<T> create() {
        return new Result();
    }
}
