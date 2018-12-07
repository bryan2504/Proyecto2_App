json.extract! leccion_hiragana, :id, :leccion, :significado, :url_imagen, :hiragana_id, :created_at, :updated_at
json.url leccion_hiragana_url(leccion_hiragana, format: :json)
