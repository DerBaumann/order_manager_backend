package ch.wegmann.pierre.order_manager.core;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id, Class c) {
        super("Could not find " + c.getSimpleName() + " " + id);
    }
}
