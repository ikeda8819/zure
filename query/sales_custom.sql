select
  sale_date,
  user_id,
  (sales_amount + delivery_charge + amount_of_consumption_tax) as total,
  delivery_charge,
  amount_of_consumption_tax
from t_sale;