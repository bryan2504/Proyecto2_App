require 'test_helper'

class LeccionKanjisControllerTest < ActionDispatch::IntegrationTest
  setup do
    @leccion_kanji = leccion_kanjis(:one)
  end

  test "should get index" do
    get leccion_kanjis_url
    assert_response :success
  end

  test "should get new" do
    get new_leccion_kanji_url
    assert_response :success
  end

  test "should create leccion_kanji" do
    assert_difference('LeccionKanji.count') do
      post leccion_kanjis_url, params: { leccion_kanji: { explicacion: @leccion_kanji.explicacion, extra: @leccion_kanji.extra, imagen_kanji: @leccion_kanji.imagen_kanji, imagen_trazos: @leccion_kanji.imagen_trazos, kanji_id: @leccion_kanji.kanji_id, leccion: @leccion_kanji.leccion, numero: @leccion_kanji.numero, significado: @leccion_kanji.significado } }
    end

    assert_redirected_to leccion_kanji_url(LeccionKanji.last)
  end

  test "should show leccion_kanji" do
    get leccion_kanji_url(@leccion_kanji)
    assert_response :success
  end

  test "should get edit" do
    get edit_leccion_kanji_url(@leccion_kanji)
    assert_response :success
  end

  test "should update leccion_kanji" do
    patch leccion_kanji_url(@leccion_kanji), params: { leccion_kanji: { explicacion: @leccion_kanji.explicacion, extra: @leccion_kanji.extra, imagen_kanji: @leccion_kanji.imagen_kanji, imagen_trazos: @leccion_kanji.imagen_trazos, kanji_id: @leccion_kanji.kanji_id, leccion: @leccion_kanji.leccion, numero: @leccion_kanji.numero, significado: @leccion_kanji.significado } }
    assert_redirected_to leccion_kanji_url(@leccion_kanji)
  end

  test "should destroy leccion_kanji" do
    assert_difference('LeccionKanji.count', -1) do
      delete leccion_kanji_url(@leccion_kanji)
    end

    assert_redirected_to leccion_kanjis_url
  end
end
