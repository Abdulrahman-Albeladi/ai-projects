% Library ontology and circulation model.
% Sample catalog titles were generated with ChatGPT.

:- table high_order_subset_of/2.
:- table high_order_member_of/2.
:- discontiguous member_of/2.
:- discontiguous property/3.
:- discontiguous direct_property/3.
:- dynamic checked_out/3.

% Collection hierarchy.
subset_of(books, items).
subset_of(movies, items).

subset_of(fiction, books).
subset_of(nonfiction, books).

subset_of(scifi, fiction).
subset_of(fantasy, fiction).
subset_of(historical, fiction).
subset_of(romance, fiction).
subset_of(mystery, fiction).

subset_of(generalities, nonfiction).
subset_of(philosophy, nonfiction).
subset_of(religion, nonfiction).
subset_of(socialsci, nonfiction).
subset_of(language, nonfiction).
subset_of(natsci, nonfiction).
subset_of(appsci, nonfiction).
subset_of(art, nonfiction).
subset_of(literature, nonfiction).
subset_of(history, nonfiction).
subset_of(biography, nonfiction).

subset_of(action, movies).
subset_of(comedy, movies).
subset_of(drama, movies).
subset_of(whodunit, movies).
subset_of(thriller, movies).
subset_of(documentary, movies).

high_order_subset_of(X, Y) :-
    subset_of(X, Y).
high_order_subset_of(X, Z) :-
    high_order_subset_of(X, Y),
    high_order_subset_of(Y, Z).

% Fiction catalog.
member_of('The Stars Beyond the Veil', scifi).
member_of('Galactic Outpost', scifi).
member_of('Echoes of a Dying Sun', scifi).
member_of('The Last Colony', scifi).
member_of('Quantum Shadows', scifi).
member_of('The Singularity Paradox', scifi).
member_of('Voyage to the Red Planet', scifi).
member_of('The Artificial Mind', scifi).
member_of('War of the Dyson Spheres', scifi).
member_of('The Infinite Nexus', scifi).

member_of('Dragons of the Last Dawn', fantasy).
member_of('The Sorcerer''s Heir', fantasy).
member_of('The Elven King''s Curse', fantasy).
member_of('The Obsidian Blade', fantasy).
member_of('The Forest of Whispers', fantasy).
member_of('Realm of the Forgotten Gods', fantasy).
member_of('The Knight''s Oath', fantasy).
member_of('The Crystal of Ages', fantasy).
member_of('The Witch Queen''s Revenge', fantasy).
member_of('The Shadow of the Archmage', fantasy).
member_of('The Hobbit', fantasy).

member_of('The Shadow of the Crown', historical).
member_of('Echoes of a Forgotten Era', historical).
member_of('The Emperor''s Legacy', historical).
member_of('The Last Roman', historical).
member_of('The Siege of Constantinople', historical).
member_of('Daughters of the Samurai', historical).
member_of('The Queen''s Gambit', historical).
member_of('The Viking''s Saga', historical).
member_of('The Rebel''s Honor', historical).
member_of('The War of the Roses', historical).

member_of('Hearts Entwined by Fate', romance).
member_of('Love Among the Ruins', romance).
member_of('A Dance in the Moonlight', romance).
member_of('The Duke''s Secret', romance).
member_of('Beneath the Midnight Sky', romance).
member_of('A Love Rekindled', romance).
member_of('The Countess''s Heart', romance).
member_of('Whispers of Desire', romance).
member_of('Stolen Kisses at Dawn', romance).
member_of('The Enchanted Lover', romance).

member_of('Whispers in the Mist', mystery).
member_of('The Silent Witness', mystery).
member_of('The Vanishing Hour', mystery).
member_of('Murder at the Manor', mystery).
member_of('The Detective''s Dilemma', mystery).
member_of('The Midnight Clue', mystery).
member_of('Secrets of the Crypt', mystery).
member_of('The Poisoned Chalice', mystery).
member_of('The Enigma of the Lost Key', mystery).
member_of('The Haunting of Blackwood Hall', mystery).

% Nonfiction catalog.
member_of('The Encyclopedia of Modern Life', generalities).
member_of('A Guide to Information Science', generalities).
member_of('The Library of Knowledge', generalities).
member_of('An Introduction to Encyclopedic Studies', generalities).
member_of('The Evolution of Reference Materials', generalities).

member_of('The Ethics of Tomorrow', philosophy).
member_of('Reflections on the Human Condition', philosophy).
member_of('The Mind and the Cosmos', philosophy).
member_of('The Philosophy of Existence', philosophy).
member_of('Pathways to Enlightenment', philosophy).

member_of('The Sacred Texts of the World', religion).
member_of('Faith and Reason: A Dialogue', religion).
member_of('The History of Religious Thought', religion).
member_of('Exploring the Divine', religion).
member_of('Rituals and Beliefs Across Cultures', religion).

member_of('The Dynamics of Human Society', socialsci).
member_of('Globalization and Its Discontents', socialsci).
member_of('The Psychology of Communities', socialsci).
member_of('Social Structures and Change', socialsci).
member_of('The Sociology of Modern Life', socialsci).

member_of('The Origins of Language', language).
member_of('The Linguistics of Communication', language).
member_of('The Evolution of Grammar', language).
member_of('The Art of Translation', language).
member_of('Language and Power', language).

member_of('The Wonders of the Natural World', natsci).
member_of('The Science of Life', natsci).
member_of('Exploring the Universe', natsci).
member_of('The Secrets of the Earth', natsci).
member_of('The Biology of Evolution', natsci).

member_of('The Engineering of Tomorrow', appsci).
member_of('Advances in Medical Technology', appsci).
member_of('The Future of Renewable Energy', appsci).
member_of('Robotics and Artificial Intelligence', appsci).
member_of('The Chemistry of Everyday Life', appsci).

member_of('The History of Art Movements', art).
member_of('The Techniques of Master Painters', art).
member_of('The Evolution of Sculpture', art).
member_of('Art and Society: A Cultural Perspective', art).
member_of('The World of Contemporary Art', art).

member_of('The Great Works of Literature', literature).
member_of('The Art of Storytelling', literature).
member_of('The Evolution of Poetry', literature).
member_of('Literary Criticism Through the Ages', literature).
member_of('The World''s Greatest Novelists', literature).

member_of('The Rise and Fall of Great Civilizations', history).
member_of('A History of the Modern World', history).
member_of('The Great Wars and Their Impact', history).
member_of('The Chronicles of Ancient Empires', history).
member_of('The Cultural Revolutions of the 20th Century', history).

member_of('The Life of a Visionary', biography).
member_of('A Journey Through Time: A Memoir', biography).
member_of('The Story of a Revolutionary Leader', biography).
member_of('The Untold Story of a Pioneer', biography).
member_of('A Portrait of Courage: The Life of a Hero', biography).
member_of('Steve Jobs', biography).

% Film catalog.
member_of('Rise of the Titans', action).
member_of('Bulletproof Legacy', action).
member_of('The Last Stand', action).
member_of('Stormbreaker', action).
member_of('Code of Honor', action).

member_of('The Misadventures of Max', comedy).
member_of('Laughing All the Way', comedy).
member_of('Weekend Shenanigans', comedy).
member_of('The Clumsy Detective', comedy).
member_of('Chaos at the Wedding', comedy).

member_of('The Weight of Silence', drama).
member_of('A Life Unraveled', drama).
member_of('Beneath the Surface', drama).
member_of('Ties That Bind', drama).
member_of('The Heart''s Journey', drama).

member_of('The Vanishing Room', whodunit).
member_of('Shadows in the Fog', whodunit).
member_of('The Secret of Hollow Creek', whodunit).
member_of('The Unseen Clue', whodunit).
member_of('Whispers of the Past', whodunit).

member_of('The Edge of Darkness', thriller).
member_of('Silent Pursuit', thriller).
member_of('Beneath the Ice', thriller).
member_of('Double Cross', thriller).
member_of('The Hidden Enemy', thriller).

member_of('Journey to the Edge of the Earth', documentary).
member_of('The Untold Stories of History', documentary).
member_of('Life in the Deep Ocean', documentary).
member_of('The Rise and Fall of Civilizations', documentary).
member_of('The Wonders of the Human Mind', documentary).
member_of('The Last Dance', documentary).

high_order_member_of(Item, Category) :-
    member_of(Item, Category).
high_order_member_of(Item, Category) :-
    member_of(Item, DirectCategory),
    high_order_subset_of(DirectCategory, Category).

% Properties inherit from an item's direct category and its ancestors.
direct_property(format, dvd, movies).
direct_property(format, hardcover, nonfiction).
direct_property(format, paperback, fiction).

property(Property, Value, Item) :-
    direct_property(Property, Value, Item).
property(Property, Value, Item) :-
    ( member_of(Item, Category)
    ; subset_of(Item, Category)
    ),
    property(Property, Value, Category).

current_date(date(Year, Month, Day)) :-
    get_time(Timestamp),
    stamp_date_time(Timestamp, date(Year, Month, Day, _, _, _, _, _, _), 'UTC').

check_out(Name, Title) :-
    current_date(Date),
    assertz(checked_out(Name, Title, Date)).

return(Name, Title) :-
    retract(checked_out(Name, Title, _)).

is_out(Title) :-
    checked_out(_, Title, _).

has_out(Name) :-
    checked_out(Name, _, _).

% Approximate calendar-day arithmetic retained for the fourteen-day policy.
days(date(Year, Month, Day), Days) :-
    Days is Day + Month * 30 + Year * 365.

overdue(Name, Title) :-
    checked_out(Name, Title, CheckedOutDate),
    current_date(CurrentDate),
    days(CurrentDate, CurrentDays),
    days(CheckedOutDate, CheckedOutDays),
    DaysOut is CurrentDays - CheckedOutDays,
    DaysOut > 14.

set_language_english :-
    member_of(Item, _),
    \+ direct_property(lang, _, Item),
    assertz(direct_property(lang, english, Item)),
    fail.
set_language_english.

set_language_spanish(0) :-
    !.
set_language_spanish(Count) :-
    member_of(Item, _),
    direct_property(lang, english, Item),
    retract(direct_property(lang, english, Item)),
    assertz(direct_property(lang, spanish, Item)),
    Remaining is Count - 1,
    set_language_spanish(Remaining).

initialize_languages :-
    set_language_english,
    set_language_spanish(10).

next_item(Item, SuggestedItem) :-
    member_of(Item, Category),
    member_of(SuggestedItem, Category),
    Item \= SuggestedItem.

next_item_lang(Item, SuggestedItem) :-
    direct_property(lang, Language, Item),
    member_of(SuggestedItem, _),
    direct_property(lang, Language, SuggestedItem),
    Item \= SuggestedItem.

next_item_from_items(Items, SuggestedItem) :-
    member(Item, Items),
    member_of(SuggestedItem, Category),
    high_order_member_of(Item, Category),
    \+ member(SuggestedItem, Items).

direct_property(similar_category, fantasy, historical).
direct_property(similar_category, mystery, scifi).
direct_property(similar_category, historical, romance).
direct_property(similar_category, romance, mystery).
direct_property(similar_category, scifi, fantasy).

next_item_include_similar_categories(Item, SuggestedItem) :-
    member_of(Item, Category),
    ( member_of(SuggestedItem, Category)
    ; direct_property(similar_category, Category, SimilarCategory),
      member_of(SuggestedItem, SimilarCategory)
    ),
    Item \= SuggestedItem.

direct_property(min_age, 16, biography).
direct_property(min_age, 10, philosophy).
direct_property(min_age, 12, socialsci).
direct_property(min_age, 15, art).
direct_property(min_age, 14, generalities).
direct_property(min_age, 8, religion).
direct_property(min_age, 9, language).
direct_property(min_age, 13, natsci).
direct_property(min_age, 11, appsci).
direct_property(min_age, 17, literature).
direct_property(min_age, 18, history).

next_item_similar_age(Item, SuggestedItem) :-
    member_of(Item, ItemCategory),
    direct_property(min_age, ItemAge, ItemCategory),
    member_of(SuggestedItem, SuggestedCategory),
    SuggestedCategory \= ItemCategory,
    direct_property(min_age, SuggestedAge, SuggestedCategory),
    SuggestedAge < ItemAge.

next_item_similar_age(Item, SuggestedItem, AskParent) :-
    member_of(Item, ItemCategory),
    direct_property(min_age, ItemAge, ItemCategory),
    member_of(SuggestedItem, SuggestedCategory),
    SuggestedCategory \= ItemCategory,
    direct_property(min_age, SuggestedAge, SuggestedCategory),
    ( SuggestedAge =< ItemAge + 5,
      SuggestedAge > ItemAge,
      AskParent = true
    ; SuggestedAge < ItemAge,
      AskParent = false
    ).
