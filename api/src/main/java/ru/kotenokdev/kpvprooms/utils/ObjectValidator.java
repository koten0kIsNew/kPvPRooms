package ru.kotenokdev.kpvprooms.utils;

import ru.kotenokdev.kpvprooms.RoomEntry;
import ru.kotenokdev.kpvprooms.Selection;

public interface ObjectValidator {

    public String validate(RoomEntry entry);

    public String validate(Selection selection, boolean nothing);
}
