# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 2018_06_12_031808) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "administrators", force: :cascade do |t|
    t.string "email", default: "", null: false
    t.string "encrypted_password", default: "", null: false
    t.string "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer "sign_in_count", default: 0, null: false
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.inet "current_sign_in_ip"
    t.inet "last_sign_in_ip"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["email"], name: "index_administrators_on_email", unique: true
    t.index ["reset_password_token"], name: "index_administrators_on_reset_password_token", unique: true
  end

  create_table "curiosidades_japons", force: :cascade do |t|
    t.string "tipo"
    t.string "explicacion"
    t.string "url_imagen"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.string "fecha"
  end

  create_table "hiraganas", force: :cascade do |t|
    t.string "leccion"
    t.string "explicacion"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "kanjis", force: :cascade do |t|
    t.string "leccion"
    t.string "explicacion"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.string "parte"
  end

  create_table "katakanas", force: :cascade do |t|
    t.string "leccion"
    t.string "explicacion"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "leccion_hiraganas", force: :cascade do |t|
    t.string "leccion"
    t.string "significado"
    t.string "url_imagen"
    t.bigint "hiragana_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["hiragana_id"], name: "index_leccion_hiraganas_on_hiragana_id"
  end

  create_table "leccion_kanjis", force: :cascade do |t|
    t.string "leccion"
    t.string "numero"
    t.string "significado"
    t.string "imagen_kanji"
    t.string "imagen_trazos"
    t.string "explicacion"
    t.string "extra"
    t.bigint "kanji_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["kanji_id"], name: "index_leccion_kanjis_on_kanji_id"
  end

  create_table "leccion_katakanas", force: :cascade do |t|
    t.string "leccion"
    t.string "significado"
    t.string "url_imagen"
    t.bigint "katakana_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["katakana_id"], name: "index_leccion_katakanas_on_katakana_id"
  end

  create_table "users", force: :cascade do |t|
    t.string "provider", default: "email", null: false
    t.string "uid", default: "", null: false
    t.string "encrypted_password", default: "", null: false
    t.string "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.boolean "allow_password_change", default: false
    t.datetime "remember_created_at"
    t.integer "sign_in_count", default: 0, null: false
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.string "current_sign_in_ip"
    t.string "last_sign_in_ip"
    t.string "confirmation_token"
    t.datetime "confirmed_at"
    t.datetime "confirmation_sent_at"
    t.string "unconfirmed_email"
    t.string "name"
    t.string "nickname"
    t.string "image"
    t.string "email"
    t.json "tokens"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["confirmation_token"], name: "index_users_on_confirmation_token", unique: true
    t.index ["email"], name: "index_users_on_email", unique: true
    t.index ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true
    t.index ["uid", "provider"], name: "index_users_on_uid_and_provider", unique: true
  end

  add_foreign_key "leccion_hiraganas", "hiraganas"
  add_foreign_key "leccion_kanjis", "kanjis"
  add_foreign_key "leccion_katakanas", "katakanas"
end
