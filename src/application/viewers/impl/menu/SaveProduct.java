package src.application.viewers.impl.menu;

import src.domain.adapters.ISaveProduct;
import src.domain.usecase.SaveProductUseCase;
import src.application.viewers.EventMenu;

public class SaveProduct implements EventMenu {
    private final ISaveProduct useCase = new SaveProductUseCase();

    @Override
    public void execute() {
        useCase.execute();
    }
}
