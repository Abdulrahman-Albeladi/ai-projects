% Apartment logic puzzle solver.
% Each unit is represented as h(Tenant, Pet, Phone, Sport, Type).

pig_tenant(Tenant) :-
    units(Units),
    member(h(Tenant, pig, _, _, _), Units).

frisbee_player(Player) :-
    units(Units),
    member(h(Player, _, _, frisbee, _), Units).

units(Units) :-
    length(Units, 5),
    member(h(professor, dog, _, _, _), Units),
    member(h(_, _, motorola, _, studio), Units),
    next_to(h(_, _, motorola, _, studio), h(_, kitten, _, _, _), Units),
    member(h(_, _, _, soccer, ph), Units),
    Units = [_, _, _, _, h(_, _, _, soccer, ph)],
    member(h(singer, _, _, tennis, _), Units),
    member(h(student, _, _, _, _), Units),
    next_to(h(student, _, _, _, _), h(_, _, _, _, onebd), Units),
    next_to(h(_, _, _, soccer, ph), h(_, _, _, _, threebd), Units),
    member(h(_, _, _, basketball, _), Units),
    Units = [_, _, h(_, _, _, basketball, _), _, h(_, _, _, soccer, ph)],
    member(h(barista, _, _, _, twobd), Units),
    Units = [h(student, _, _, _, _)|_],
    member(h(_, _, google, _, _), Units),
    member(h(_, fish, _, _, _), Units),
    next_to(h(_, _, google, _, _), h(_, fish, _, _, _), Units),
    member(h(_, _, nokia, pickleball, _), Units),
    member(h(_, hamster, samsung, _, _), Units),
    member(h(entrepreneur, _, iphone, _, _), Units).

% Units are adjacent when they appear consecutively in either order.
next_to(Unit1, Unit2, Units) :-
    append(_, [Unit1, Unit2|_], Units).
next_to(Unit1, Unit2, Units) :-
    append(_, [Unit2, Unit1|_], Units).
