json.extract! leccion_kanji, :id, :leccion, :numero, :significado, :imagen_kanji, :imagen_trazos, :explicacion, :extra, :kanji_id, :created_at, :updated_at
json.url leccion_kanji_url(leccion_kanji, format: :json)
