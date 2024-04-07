create table bonuses (monto float4 not null, id bigserial not null, disponibilidad varchar(255), marca varchar(255), primary key (id));
create table discount_repairs_entity (diesel float4 not null, electrico float4 not null, gasolina float4 not null, hibirido float4 not null, reparaciones_maximas integer not null, reparaciones_minimas integer not null, id bigserial not null, primary key (id));
create table repairs_type (cost_diesel float4 not null, cost_electric float4 not null, cost_gasoline float4 not null, cost_hybrid float4 not null, id bigserial not null, description varchar(255), type varchar(255), primary key (id));
create table reparaciones (hora_entrega_cliente time(6), hora_ingreso_taller time(6), hora_salida_reparacion time(6), monto_total_reparacion float4, fecha_entrega_cliente timestamp(6), fecha_ingreso_taller timestamp(6), fecha_salida_reparacion timestamp(6), id bigserial not null, patente_vehiculo varchar(255), tipo_reparacion varchar(255), primary key (id));
create table surcharge_mileage_entity (furgoneta float4 not null, hatchback float4 not null, kilometraje_maximo integer not null, kilometraje_minimo integer not null, pickup float4 not null, sedan float4 not null, suv float4 not null, id bigserial not null, primary key (id));
create table surcharge_seniority_entity (furgoneta float4 not null, hatchback float4 not null, pickup float4 not null, sedan float4 not null, suv float4 not null, years_max integer not null, years_min integer not null, id bigserial not null, primary key (id));
create table vehiculos (numero_reparaciones integer not null, id bigserial not null, anio_fabricacion varchar(255), marca varchar(255), modelo varchar(255), numero_asientos varchar(255), patente varchar(255) not null unique, tipo varchar(255), tipo_motor varchar(255), primary key (id));create table bonuses (monto float4 not null, id bigserial not null, disponibilidad varchar(255), marca varchar(255), primary key (id));
create table bonuses (monto float4 not null, id bigserial not null, disponibilidad varchar(255), marca varchar(255), primary key (id));
create table discount_repairs (diesel float4 not null, electrico float4 not null, gasolina float4 not null, hibirido float4 not null, reparaciones_maximas integer not null, reparaciones_minimas integer not null, id bigserial not null, primary key (id));
create table repairs_type (cost_diesel float4 not null, cost_electric float4 not null, cost_gasoline float4 not null, cost_hybrid float4 not null, id bigserial not null, description varchar(255), type varchar(255), primary key (id));
create table reparaciones (hora_entrega_cliente time(6), hora_ingreso_taller time(6), hora_salida_reparacion time(6), monto_total_reparacion float4, fecha_entrega_cliente timestamp(6), fecha_ingreso_taller timestamp(6), fecha_salida_reparacion timestamp(6), id bigserial not null, patente_vehiculo varchar(255), tipo_reparacion varchar(255), primary key (id));
create table surcharge_mileage (furgoneta float4 not null, hatchback float4 not null, kilometraje_maximo integer not null, kilometraje_minimo integer not null, pickup float4 not null, sedan float4 not null, suv float4 not null, id bigserial not null, primary key (id));
create table surcharge_seniority (furgoneta float4 not null, hatchback float4 not null, pickup float4 not null, sedan float4 not null, suv float4 not null, years_max integer not null, years_min integer not null, id bigserial not null, primary key (id));
create table vehiculos (numero_reparaciones integer not null, id bigserial not null, anio_fabricacion varchar(255), marca varchar(255), modelo varchar(255), numero_asientos varchar(255), patente varchar(255) not null unique, tipo varchar(255), tipo_motor varchar(255), primary key (id));