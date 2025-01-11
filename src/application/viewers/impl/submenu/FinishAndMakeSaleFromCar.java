package src.application.viewers.impl.submenu;

import src.application.viewers.EventMenu;
import src.domain.adapters.IFinishAndMakeSaleFromCar;
import src.domain.usecase.FinishAndMakeSaleFromCarUseCase;

public class FinishAndMakeSaleFromCar implements EventMenu {
    private final IFinishAndMakeSaleFromCar useCase = new FinishAndMakeSaleFromCarUseCase();

    @Override
    public void execute() {
        useCase.execute();
    }
}
