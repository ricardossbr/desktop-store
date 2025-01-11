package src.application.viewers.impl.submenu;

import src.application.viewers.EventMenu;
import src.domain.adapters.IFinishCar;
import src.domain.usecase.FinishCarUseCase;

public class FinishCar implements EventMenu {

    private final IFinishCar useCase = new FinishCarUseCase();

    @Override
    public void execute() {
        useCase.execute();
    }
}
