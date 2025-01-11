package src.application.viewers.impl.submenu;

import src.application.viewers.EventMenu;
import src.domain.adapters.IAddProductFromCar;
import src.domain.usecase.AddProductFromCarUseCase;

public class AddProductFromCar implements EventMenu {

    private final IAddProductFromCar useCase = new AddProductFromCarUseCase();

    @Override
    public void execute() {
        useCase.execute();
    }
}
