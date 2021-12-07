select
id,
name,
address
from shop a
inner join shop_detail b
  on a.id = b.id
where delete_flg = 0;