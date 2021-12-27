select
a.id as id,
a.category as category,
a.title as title
from neta a
inner join neta b
  on a.id = b.id
where 1 = 1;