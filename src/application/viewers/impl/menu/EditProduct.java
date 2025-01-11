package src.application.viewers.impl.menu;

import src.domain.adapters.IEditProduct;
import src.domain.usecase.EditProductUseCase;
import src.application.viewers.EventMenu;

public class EditProduct implements EventMenu {
    private final IEditProduct useCase = new EditProductUseCase();

    @Override
    public void execute() {
        useCase.execute();
    }
}
