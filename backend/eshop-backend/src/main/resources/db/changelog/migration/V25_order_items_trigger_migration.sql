CREATE OR REPLACE FUNCTION public.update_orders_totals() 
RETURNS TRIGGER AS $$
BEGIN
    UPDATE orders o 
    SET subtotal = COALESCE(sub.sum_total, 0), 
        total_amount = COALESCE(sub.sum_total, 0) + o.tax_amount
    FROM (
        SELECT  
            ord_ids.order_id,
            SUM(ii.line_total) AS sum_total
        FROM (
            SELECT order_id FROM new_table
            UNION
            SELECT order_id FROM old_table
        ) ord_ids
        LEFT JOIN order_items ii 
            ON ii.order_id = ord_ids.order_id
        GROUP BY ord_ids.order_id
    ) sub
    WHERE o.order_id = sub.order_id;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_order_totals_insert
AFTER INSERT ON order_items
REFERENCING NEW TABLE AS new_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_orders_totals();

CREATE TRIGGER trg_update_order_totals_update
AFTER UPDATE ON order_items
REFERENCING NEW TABLE AS new_table OLD TABLE AS old_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_orders_totals();

CREATE TRIGGER trg_update_order_totals_delete
AFTER DELETE ON order_items
REFERENCING OLD TABLE AS old_table
FOR EACH STATEMENT
EXECUTE FUNCTION public.update_orders_totals();