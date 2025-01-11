package src.application.viewers.impl.submenu;

import src.application.viewers.EventMenu;
import src.domain.adapters.IRemoveProductFromCar;
import src.domain.usecase.RemoveProductFromCarUseCase;

public class RemoveProductFromCar implements EventMenu {
    private final IRemoveProductFromCar useCase = new RemoveProductFromCarUseCase();

    @Override
    public void execute() {
        useCase.execute();
    }
}
