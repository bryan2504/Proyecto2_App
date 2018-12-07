class LeccionKanjisController < ApplicationController
  before_action :set_leccion_kanji, only: [:show, :edit, :update, :destroy]

  # GET /leccion_kanjis
  # GET /leccion_kanjis.json
  def index
    @leccion_kanjis = LeccionKanji.all
  end

  # GET /leccion_kanjis/1
  # GET /leccion_kanjis/1.json
  def show
  end

  # GET /leccion_kanjis/new
  def new
    @leccion_kanji = LeccionKanji.new
  end

  # GET /leccion_kanjis/1/edit
  def edit
  end

  # POST /leccion_kanjis
  # POST /leccion_kanjis.json
  def create
    @leccion_kanji = LeccionKanji.new(leccion_kanji_params)

    respond_to do |format|
      if @leccion_kanji.save
        format.html { redirect_to @leccion_kanji, notice: 'Leccion kanji was successfully created.' }
        format.json { render :show, status: :created, location: @leccion_kanji }
      else
        format.html { render :new }
        format.json { render json: @leccion_kanji.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /leccion_kanjis/1
  # PATCH/PUT /leccion_kanjis/1.json
  def update
    respond_to do |format|
      if @leccion_kanji.update(leccion_kanji_params)
        format.html { redirect_to @leccion_kanji, notice: 'Leccion kanji was successfully updated.' }
        format.json { render :show, status: :ok, location: @leccion_kanji }
      else
        format.html { render :edit }
        format.json { render json: @leccion_kanji.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /leccion_kanjis/1
  # DELETE /leccion_kanjis/1.json
  def destroy
    @leccion_kanji.destroy
    respond_to do |format|
      format.html { redirect_to leccion_kanjis_url, notice: 'Leccion kanji was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_leccion_kanji
      @leccion_kanji = LeccionKanji.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def leccion_kanji_params
      params.require(:leccion_kanji).permit(:leccion, :numero, :significado, :imagen_kanji, :imagen_trazos, :explicacion, :extra, :kanji_id)
    end
end
